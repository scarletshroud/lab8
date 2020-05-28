package src.commands;

import src.client.Client;
import src.logic.CollectionManager;

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
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
        return collectionManager.history();
    }
}
