package src.commands;

import src.client.Client;
import src.exceptions.BadNumberOfArgsException;
import src.logic.CollectionManager;

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
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
            return collectionManager.info();
    }
}