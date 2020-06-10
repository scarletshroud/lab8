package src.logic;

import src.elements.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.xml.bind.ValidationException;

/**
 * Class of Initializer.
 * This class initializes collection.
 */

public class Initializer {
    private static int offset = 0;

    /**
     * Constructor
     */

    public Initializer() {
    }

    /**
     * Method for initialization of collection
     * @param product - the element of collection
     * @param values - the fields of element
     * @throws NullPointerException
     * @throws ValidationException
     */

    public static boolean Initialize(Product product, String[] values) throws NullPointerException, ValidationException {
        try {
            product.setName(values[offset++]);
            Coordinates coordinates = new Coordinates();
            coordinates.setX(Float.parseFloat(values[offset++]));
            coordinates.setY(Double.parseDouble(values[offset++]));
            product.setCoordinates(coordinates);
            product.setCreationDate(LocalDate.parse(values[offset++]));
            product.setPrice(Long.parseLong(values[offset++]));
            product.setPartNumber(values[offset++]);
            product.setUnitOfMeasure(values[offset++]);
            Person person = new Person();
            person.setName(values[offset++]);
            person.setHeight(Integer.parseInt(values[offset++]));
            person.setEyeColor(values[offset++]);
            Location location = new Location();
            location.setX(Long.parseLong(values[offset++]));
            location.setY(Long.parseLong(values[offset++]));
            location.setZ(Integer.parseInt(values[offset++]));
            location.setName(values[offset++]);
            person.setLocation(location);
            product.setOwner(person);
        } catch (NullPointerException | NumberFormatException ex) {
            System.err.println("The field can not be null! Check the file.");
            return false;
        } catch (ValidationException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
