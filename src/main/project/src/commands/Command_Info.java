package src.commands;

import src.client.Client;
import src.database.User;
import src.exceptions.BadNumberOfArgsException;
import src.logic.CollectionManager;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command Info.
 * This command shows info about collection.
 */

public class Command_Info extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Info() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Info in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().info();
        }
        return "You don't have rights to interact with collection!";
    }
}