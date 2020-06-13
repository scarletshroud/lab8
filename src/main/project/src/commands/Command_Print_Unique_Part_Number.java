package src.commands;

import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command Print Unique Part Number.
 * This command prints unique values of field "Part Number" of src.elements of collection.
 */

public class Command_Print_Unique_Part_Number extends Command implements Serializable {

    /**
     * Constructor.
     */

    public Command_Print_Unique_Part_Number() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    /**
     * Execute method Print Unique Part Number in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().printUniquePartNumber();
        }
        return "You don't have rights to interact with collection!";
    }
}
