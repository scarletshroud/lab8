package src.commands;

import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class of command Exit.
 * This command stops the app.
 */

public class Command_Exit extends Command implements Serializable {

    /**
     * Constructor.
     */

    public Command_Exit() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Exit in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
       return null;
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, Object args) {
       return null;
    }
}
