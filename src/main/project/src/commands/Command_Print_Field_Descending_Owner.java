package src.commands;

import src.client.Client;
import src.logic.CollectionManager;

import java.io.Serializable;

/**
 * Class of command Print Field Descending Owner
 * This command prints owners of products in decreasing order.
 */

public class Command_Print_Field_Descending_Owner extends Command implements Serializable {

    /**
     * Constructor.
     */

    public Command_Print_Field_Descending_Owner() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Print Field Descending Owner in Collection Manager.
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
        return collectionManager.printFieldDescendingOwner();
    }
}
