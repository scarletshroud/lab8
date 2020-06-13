package src.commands;

import src.database.User;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command show.
 * This command shows collection in string presentation.
 */

public class Command_Show extends Command implements Serializable {

    /**
     * Constructor.
     */

    public Command_Show() {}

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Show in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().show();
        }
        return "You don't have rights to interact with collection!";
    }

}
