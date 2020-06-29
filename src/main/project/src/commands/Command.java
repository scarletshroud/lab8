package src.commands;

import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Command {

    public abstract boolean validateArgs(String ... args);
    public abstract ServerPacket executeOnServer(Server server, User user, Object object);
    public Packet executeOnClient(boolean authorized, User user, String ... args) {
        if (authorized) {
            Packet packet = new Packet();
            packet.wrap(this, user);
            return packet;
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }
}
