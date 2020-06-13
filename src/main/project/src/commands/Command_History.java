package src.commands;

import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
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
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().history();
        }
        return "You don't have rights to interact with collection!";
    }
}
