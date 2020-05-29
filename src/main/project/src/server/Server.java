package src.server;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.logic.CollectionManager;
import src.logic.Packet;

import javax.xml.bind.ValidationException;
import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable{

    private final int port;
    private final int BUFFER_SIZE = 4096;

    private ServerSocketChannel serverSocket;
    private Selector selector;
    private CollectionManager collectionManager;

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    private boolean isWorking;


    public Server(int port) {
        this.port = port;
        isWorking = true;
    }

    public static void main(String[] args) {
        Server server = new Server(258);
        server.run();
    }


    public void run() {
        try {

        collectionManager = new CollectionManager();
        logger.info("Collection was initialized correctly.");

        selector = Selector.open();

        serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress("localhost", port));
        serverSocket.configureBlocking(false);
        logger.info("Server is working on:" + serverSocket.getLocalAddress());

        int ops = serverSocket.validOps();
        SelectionKey selectionKey = serverSocket.register(selector, ops, null);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (!(reader.ready() && reader.readLine().trim().equals("exit"))) {

            try {

                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectedKeys.iterator();

                while (it.hasNext()) {

                    SelectionKey key = it.next();

                    if (key.isAcceptable()) {
                        acceptConnection(serverSocket, selector);
                    } else if (key.isReadable()) {
                        acceptRequest(key);
                    }
                    it.remove();

                }
            } catch (IOException ex){}
        }

        collectionManager.save();
        serverSocket.close();

        } catch (IOException | ValidationException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void acceptConnection(ServerSocketChannel serverSocket, Selector selector) throws IOException {
        SocketChannel socket = serverSocket.accept();
        socket.configureBlocking(false);
        socket.register(selector, SelectionKey.OP_READ);
        logger.info("Client has connected from:" + socket.getRemoteAddress());
    }

    private void acceptRequest(SelectionKey key) throws IOException {

        logger.info("Server started accepting the new request.");

        SocketChannel socket = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        socket.read(buffer);
        if (buffer.position() > 0) {
            byte[] bytes = buffer.array();
            Packet packet = deserialize(bytes);
            if (packet != null) {
                String answer = packet.getCommand().executeOnServer(collectionManager, packet.getArgument());

                if (answer != null) {
                    buffer.clear();
                    bytes = answer.getBytes(StandardCharsets.UTF_8);
                    buffer = ByteBuffer.wrap(bytes);

                    while (buffer.hasRemaining()) {
                        socket.write(buffer);
                    }
                }
            }
        }
    }

   private Packet deserialize(byte[] bytes) {
        try {
            ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (Packet) input.readObject();
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
   }

}