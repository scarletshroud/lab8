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
 * Class of command Add If Max.
 * This command adds a new element to collection if it is a max.
 */
public class Command_Add_If_Max extends Command implements Serializable {

    /**
     * Simple constructor.
     */

    public Command_Add_If_Max() {}

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 1;
    }

    /**
     * Execute method Add If Max in Collection Manager.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().addIfMax(object), true, true);
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

    private Product buildProduct(String[] args) {

        Product product = new Product();
        Coordinates coordinates = new Coordinates();
        Location location = new Location();
        Person person = new Person();
        product.setName(args[0]);
        product.setHost(args[13]);

        product.setCreationDate(LocalDate.now());

        try {
            coordinates.setX(Float.parseFloat(args[1]));
            coordinates.setY(Double.parseDouble(args[2]));

            location.setX(Long.parseLong(args[10]));
            location.setY(Long.parseLong(args[11]));
            location.setZ(Integer.parseInt(args[12]));

            location.setName(args[9]);
            person.setName(args[6]);

            person.setHeight(Integer.parseInt(args[7]));
            person.setEyeColor(args[8]);

            person.setLocation(location);

            product.setPrice(Long.parseLong(args[3]));

            product.setPartNumber(args[4]);

            product.setUnitOfMeasure(args[5]);

            product.setCoordinates(coordinates);
            product.setOwner(person);
            return product;

        } catch (ValidationException ex) {
            System.err.println(ex.getMessage());
            return null;
        }
    }
}
