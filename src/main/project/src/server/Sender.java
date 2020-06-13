package src.server;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Sender implements Runnable {

    private SocketChannel socket;
    private String answer;
    private ByteBuffer buffer;
    private byte[] bytes;

    Sender(SocketChannel socket, String answer, ByteBuffer buffer, byte[] bytes) {
        this.socket = socket;
        this.answer = answer;
        this.buffer = buffer;
        this.bytes = bytes;
    }

    @Override
    public void run() {

        try {
            buffer.clear();
            bytes = answer.getBytes(StandardCharsets.UTF_8);
            buffer = ByteBuffer.wrap(bytes);

            while (buffer.hasRemaining()) {
                socket.write(buffer);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
