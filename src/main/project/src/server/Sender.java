package src.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Sender implements Runnable {

    private SocketChannel socket;
    private String answer;
    private final int BUFFER_SIZE = 4096;

    Sender(SocketChannel socket, String answer) {
        this.socket = socket;
        this.answer = answer;
    }

    @Override
    public void run() {
        try {

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            buffer.clear();
            byte[] bytes;

            if (answer != null) {
                 bytes = answer.getBytes(StandardCharsets.UTF_8);
            } else {
                 bytes = "Something wrong.".getBytes(StandardCharsets.UTF_8);
            }

            buffer = ByteBuffer.wrap(bytes);

            while (buffer.hasRemaining()) {
                socket.write(buffer);
            }
            notify();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            notify();
        }

    }

}