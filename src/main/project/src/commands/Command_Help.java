package src.commands;

import src.client.Client;
import src.exceptions.BadNumberOfArgsException;
import src.logic.CollectionManager;

import java.io.Serializable;

/**
 * Class of command Help.
 * This command shows the list of available src.commands.
 */

public class Command_Help extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Help() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Help in Collection Manager.
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
        return collectionManager.help();
    }
}
