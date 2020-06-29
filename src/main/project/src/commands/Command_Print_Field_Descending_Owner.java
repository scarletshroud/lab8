package src.commands;

import src.database.User;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command Print Field Descending Owner
 * This command prints owners of products in decreasing order.
 */

public class Command_Print_Field_Descending_Owner extends Command implements Serializable {

    /**
     * Constructor.
     */

    public Command_Print_Field_Descending_Owner() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Print Field Descending Owner in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().printFieldDescendingOwner(), true, true);
        }
        return new ServerPacket(null, "You don't have rights to interact with collection!", false, true);
    }
}
