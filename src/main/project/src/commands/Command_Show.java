package src.commands;

import src.client.Client;
import src.logic.CollectionManager;

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
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
            return collectionManager.show();
    }

}
