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
    public Packet executeOnClient(boolean authorized, User user, String ... args) {
        if (authorized) {
            try {
                int id = Integer.parseInt(args[0]);
                String name = args[1];
                Object[] argsToSend = new Object[]{id, name};
                return new Packet(this, user, argsToSend);
            } catch (NumberFormatException ex) {
                System.out.println("Arguments aren't correct.");
            }
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }
}
