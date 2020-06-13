package src.commands;

import src.database.User;
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
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().printFieldDescendingOwner();
        }
        return "You don't have rights to interact with collection!";
    }
}
