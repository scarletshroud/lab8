package src.server;

import src.logic.ServerPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Sender implements Runnable {

    private SocketChannel socket;
    private ServerPacket answer;
    private final int BUFFER_SIZE = 4096;

    Sender(SocketChannel socket, ServerPacket answer) {
        this.socket = socket;
        this.answer = answer;
    }

    @Override
    public void run() {
        try {

            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            buffer.clear();

            byte[] bytes = serializeObject(answer);
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

    private byte[] serializeObject(ServerPacket packet) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }

}