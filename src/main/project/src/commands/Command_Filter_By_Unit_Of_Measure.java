package src.commands;

import com.sun.org.glassfish.gmbal.ManagedObject;
import src.client.Client;
import src.database.User;
import src.logic.CollectionManager;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.Serializable;

/**
 * Class of command Filter By Unit Of Measure.
 * This command filters collection by unit of measure.
 */

public class Command_Filter_By_Unit_Of_Measure extends Command implements Serializable {

    /**
     * Constructor
     */

    public Command_Filter_By_Unit_Of_Measure() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 1;
    }

    /**
     * Execute method Filter By Unit Of Measure in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().filterByUnitOfMeasure(object), true, true);
        }
        return new ServerPacket(null,"You don't have rights to interact with collection!", false, true);
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, String ... args) {
        if (authorized) {
            return new Packet(this, user, args[0]);
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }
}
