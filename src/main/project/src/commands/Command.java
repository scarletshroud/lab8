package src.commands;

import src.client.Client;
import src.logic.CollectionManager;
import src.logic.Packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Command {

    public abstract boolean validateArgs(String ... args);
    public abstract String executeOnServer(CollectionManager collectionManager, Object object);
    public Packet executeOnClient(String ... args) {
        Packet packet = new Packet();
        packet.wrap(this);
        return packet;
    }
}
