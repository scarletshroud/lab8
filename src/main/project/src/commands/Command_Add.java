package src.commands;

import src.client.Client;
import src.database.User;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Class of the command Add.
 * This command adds a new element to collection.
 */

public class Command_Add extends Command implements Serializable {

    /**
     * Simple constructor.
     */

    public Command_Add() {}

    @Override
    public boolean validateArgs(String ... args) {
        //Добавить обработку типов команды
       return args.length == 1;
    }

    /**
     * Execute method add in Collection Manager.
     * @param server - the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object)  {
            if (server.checkUser(user.getLogin(), user.getPassword())) {
                return new ServerPacket(null, server.getCollectionManager().add(object), true, true);
            }
            return new ServerPacket(null, "You don't have rights to interact with collection!", false, true);
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, Object args) {
        if (authorized) {
            Product product = (Product) args;
            if (product != null) {
                return new Packet(this, user, product);
            } else {
                System.out.println("Some problems with adding a product.");
                return null;
            }
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }
}
