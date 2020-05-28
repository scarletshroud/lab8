package src.commands;

import src.client.Client;
import src.logic.CollectionManager;
import src.logic.Packet;

import java.io.Serializable;

/**
 * Class of command Remove By Id.
 * This command removes an element of collection by id.
 */

public class Command_Remove_By_Id extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Remove_By_Id() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 1;
    }

    /**
     * Execute method Remove By Id in Collection Manager.
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
        return collectionManager.removeById(object);
    }

    @Override
    public Packet executeOnClient(String ... args) {
        Integer id = Integer.parseInt(args[0]);
        return new Packet(this, id);
    }
}
