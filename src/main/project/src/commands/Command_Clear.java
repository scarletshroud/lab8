package src.commands;

import src.client.Client;
import src.database.User;
import src.exceptions.BadNumberOfArgsException;
import src.logic.CollectionManager;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command clear.
 * This command clears the collection.
 */

public class Command_Clear extends Command implements Serializable {

    /**
     * Simple constructor.
     */

    public Command_Clear() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Clear in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().clear(user);
        }
        return "You don't have rights to interact with collection!";
    }
}
