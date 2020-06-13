package src.commands;

import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
import src.logic.Packet;
import src.server.Server;

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
     * @param server -the manager of collection
     */

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return server.getCollectionManager().removeById(user, object);
        }
        return "You don't have rights to interact with collection!";
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, String ... args) {
        if (authorized) {
            Integer id = Integer.parseInt(args[0]);
            return new Packet(this, user, id);
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }
}
