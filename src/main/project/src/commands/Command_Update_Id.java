package src.commands;

import src.client.Client;
import src.logic.CollectionManager;
import src.logic.Packet;

import java.io.Serializable;

/**
 * Class of command Update Id.
 * This command updates id of chosen element in collection.
 */

public class Command_Update_Id extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Update_Id() {}

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 2;
    }

    /**
     * Execute method Update Id in Collection Manager.
     * @param collectionManager -the manager of collection
     */

    @Override
    public String executeOnServer(CollectionManager collectionManager, Object object) {
        return collectionManager.updateId(object);
    }

    @Override
    public Packet executeOnClient(String ... args) {
        Integer id = Integer.parseInt(args[0]);
        String name = args[1];
        Object[] argsToSend = new Object[] {id, name};
        return new Packet(this, argsToSend);
    }
}
