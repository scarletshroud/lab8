package src.commands;

import src.client.Client;
import src.exceptions.BadNumberOfArgsException;
import src.logic.CollectionManager;

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
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
            return collectionManager.clear();
    }
}
