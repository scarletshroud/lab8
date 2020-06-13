package src.commands;

import src.client.Client;
import src.database.User;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;
import src.logic.Packet;
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
    public String executeOnServer(Server server, User user, Object object)  {
            if (server.checkUser(user.getLogin(), user.getPassword())) {
                return server.getCollectionManager().add(object);
            }
            return "You don't have rights to interact with collection!";
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, String ... args) {
        if (authorized) {
            Product product = new Product();
            if (readElement(product, user, args[0])) {
                System.out.println("Ready to add element to collection!");
                return new Packet(this, user, product);
            } else {
                System.out.println("Some problems with adding a product.");
                return null;
            }
        }
        System.out.println("You must be logged in to continue working.");
        return null;
    }

    private boolean readElement(Product product, User user, String name) {
        boolean success = true;
        Coordinates coordinates = new Coordinates();
        Location location = new Location();
        Person person = new Person();
        Scanner scanner = new Scanner(System.in);
        String nextValue = new String();
        product.setName(name);
        product.setHost(user.getLogin());
        LocalDate creationDate = LocalDate.now();
        product.setCreationDate(creationDate);

        try {
            System.out.println("Enter the coordinate x of coordinates");
            nextValue = scanner.nextLine();
            coordinates.setX(Float.parseFloat(nextValue));

            System.out.println("Enter the coordinate y of coordinates");
            nextValue = scanner.nextLine();
            coordinates.setY(Double.parseDouble(nextValue));

            System.out.println("Enter the coordinate x of location");
            nextValue = scanner.nextLine();
            location.setX(Long.parseLong(nextValue));

            System.out.println("Enter the coordinate y of location");
            nextValue = scanner.nextLine();
            location.setY(Long.parseLong(nextValue));

            System.out.println("Enter the coordinate z of location");
            nextValue = scanner.nextLine();
            location.setZ(Integer.parseInt(nextValue));

            System.out.println("Enter the name of location");
            nextValue = scanner.nextLine();
            location.setName(nextValue);

            System.out.println("Enter the name of person");
            nextValue = scanner.nextLine();
            person.setName(nextValue);

            System.out.println("Enter the height of person");
            nextValue = scanner.nextLine();
            person.setHeight(Integer.parseInt(nextValue));

            System.out.println("Enter the person's color of eyes");
            nextValue = scanner.nextLine();
            person.setEyeColor(nextValue);

            person.setLocation(location);

            System.out.println("Enter the price of product");
            nextValue = scanner.nextLine();
            product.setPrice(Long.parseLong(nextValue));

            System.out.println("Enter the part number of product");
            nextValue = scanner.nextLine();
            product.setPartNumber(nextValue);

            System.out.println("Enter the unit of measure of product");
            nextValue = scanner.nextLine();
            product.setUnitOfMeasure(nextValue);

            product.setCoordinates(coordinates);
            product.setOwner(person);
        }

        catch (NumberFormatException ex) {
            if (nextValue.equals("")) {
                System.err.println("Empty string!");
            } else {
                System.err.println("Invalid value!");
            }
            success = false;
        } catch (ValidationException ex) {
            System.err.println(ex.getMessage());
            success = false;
        }

        return success;
    }
}
