package src.server;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import src.database.DBManager;
import src.logic.CollectionManager;

import java.io.*;
import java.net.InetSocketAddress;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    public static Server server;

    private final int port;

    private ServerSocketChannel serverSocket;
    private CollectionManager collectionManager;
    private DBManager dbManager;

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public Server(int port) {
        server = this;
        this.port = port;
    }

    public static void main(String[] args) {
        Server server = new Server(29666);
        server.run();
    }

    public void run() {
        try {

            dbManager = new DBManager();

            collectionManager = new CollectionManager(dbManager);
            logger.info("Collection was initialized correctly.");

            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
            logger.info("Server is working on:" + serverSocket.getLocalAddress());

            ExecutorService readerExecutor = Executors.newFixedThreadPool(20);
            ExecutorService observerExecutor = Executors.newSingleThreadExecutor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            ArrayList<SocketChannel> socketChannels = new ArrayList<>();

        //    Observer observer = new Observer();
        //    observer.start();

            while (!(reader.ready() && reader.readLine().trim().equals("exit"))) {

                if (collectionManager.isChanged()) {
                    observerExecutor.submit(new Observer(socketChannels, collectionManager));
                    collectionManager.handleChanges();
                }

                try {
                    SocketChannel socket = serverSocket.accept();
                    if (socket != null) {
                        socketChannels.add(socket);
                        socket.configureBlocking(false);
                        logger.info("Client has connected from:" + socket.getRemoteAddress());
                        Reader readerThread = new Reader(socket);
                        readerExecutor.submit(readerThread);
                    }
                }
                catch (IOException ex) {
                    System.out.println("Some problems with connection");
                }
            }

            readerExecutor.shutdown();

            dbManager.close();
            serverSocket.close();

            logger.info("Server finished his work.");
            System.out.println("Server finished his work.");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    synchronized public CollectionManager getCollectionManager() {
        logger.info("getCollection");
        return collectionManager;
    }

    synchronized public boolean checkUser(@NotNull final String login, @NotNull final String pass) {
        logger.info("checkUser");
        return dbManager.checkUser(login, pass);
    }

    synchronized public int registerUser(@NotNull final String login, @NotNull final String pass) {
        return dbManager.createUser(login, pass);
    }
}