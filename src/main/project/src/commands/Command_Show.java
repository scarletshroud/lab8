package src.commands;

import src.database.User;
import src.logic.ServerPacket;
import src.server.Server;

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
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(server.getCollectionManager().show(), null, true, false);
        }
        return new ServerPacket(null, "You don't have rights to interact with collection!", false, true);
    }

}
