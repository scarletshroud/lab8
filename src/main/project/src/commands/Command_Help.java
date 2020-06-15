package src.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.client.Client;
import src.database.User;
import src.exceptions.BadNumberOfArgsException;
import src.logic.CollectionManager;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command Help.
 * This command shows the list of available src.commands.
 */

public class Command_Help extends Command implements Serializable {

    /**
     * Constructor
     */

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public Command_Help() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Help in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        logger.info("executeCommand");
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().help();
        }
        return "You don't have rights to interact with collection!";
    }
}
