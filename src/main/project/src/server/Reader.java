package src.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.logic.Packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

public class Reader implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private final int BUFFER_SIZE = 4096;
    private SocketChannel socket;

    Reader(SocketChannel socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {

            ExecutorService executorService = Executors.newCachedThreadPool();
            ForkJoinPool pool = new ForkJoinPool(1);

            logger.info("Server started accepting the new request.");

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            socket.read(buffer);
            if (buffer.position() > 0) {
                byte[] bytes = buffer.array();
                Packet packet = deserialize(bytes);
                if (packet != null) {
                   Future<String> result = pool.submit(new Handler(packet));
                   try {
                       String answer = result.get();
                       if (answer != null) {
                           executorService.submit(new Sender(socket, answer, buffer, bytes));
                       }
                       executorService.shutdown();
                   } catch(InterruptedException | ExecutionException ex) {
                       ex.printStackTrace();
                   }
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            logger.info(ex.getMessage());
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
