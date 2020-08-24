package src.commands;

import src.client.Client;
import src.database.User;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;
import src.logic.CollectionManager;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Class of command Add If Min.
 * This src.commands adds a new element to collection if it is min.
 */

public class Command_Add_If_Min extends Command implements Serializable {

    /**
     * Simple constructor.
     */

    public Command_Add_If_Min() { }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 1;
    }

    /**
     * Execute method Add If Min in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object)  {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().addIfMin(object), true, true);
        }
        return new ServerPacket(null, "You don't have rights to interact with collection!", false, true);
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, Object args) {
        if (authorized) {
            Product product = (Product) args;
            if (product != null) {
                System.out.println("Ready to add element to collection!");
            } else {
                System.out.println("Some problems with adding a product.");
            }
            return new Packet(this, user, product);
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }

}
