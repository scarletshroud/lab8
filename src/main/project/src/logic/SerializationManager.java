package src.logic;

import java.io.*;

public class SerializationManager {

    public static byte[] serializeObject(Packet packet) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }

    public static ServerPacket deserializeObject(byte[] bytes) {
        try {
            ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return (ServerPacket) input.readObject();
        } catch(IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
