package src.commands;

import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command History.
 * This command shows the list of last used src.commands.
 */

public class Command_History extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_History() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }
    /**
     * Execute method History in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().history(), true, true);
        }
        return new ServerPacket(null, "You don't have rights to interact with collection!", false, true);
    }
}
