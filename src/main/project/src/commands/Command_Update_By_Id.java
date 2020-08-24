package src.commands;

import src.database.User;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command Update Id.
 * This command updates id of chosen element in collection.
 */

public class Command_Update_By_Id extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Update_By_Id() {}

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 2;
    }

    /**
     * Execute method Update Id in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().updateId(user, object), true, true);
        }
        return new ServerPacket(null, "You don't have rights to interact with collection!", false, true);
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, Object args) {
        if (authorized) {
            try {
                return new Packet(this, user, args);
            } catch (NumberFormatException ex) {
                System.out.println("Arguments aren't correct.");
            }
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }
}
