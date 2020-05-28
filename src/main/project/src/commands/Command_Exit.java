package src.commands;

import src.client.Client;
import src.logic.CollectionManager;
import src.logic.Packet;

import java.io.IOException;
import java.io.Serializable;

/**
 * Class of command Exit.
 * This command stops the app.
 */

public class Command_Exit extends Command implements Serializable {

    /**
     * Constructor.
     */

    public Command_Exit() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Exit in Collection Manager.
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
        collectionManager.save();
        return "Finished.";
    }
}
