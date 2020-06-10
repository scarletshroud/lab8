package src.elements;


import com.sun.istack.internal.NotNull;
import javax.xml.bind.ValidationException;
import java.io.Serializable;

/**
 * Class person which has a product.
 */

public class Person implements Comparable<Person>, Serializable {
    @NotNull
    private String name; //Строка не может быть пустой
    @NotNull
    private Integer height; //Поле не может быть null, Значение поля должно быть больше 0
    @NotNull
    private Color eyeColor; //Поле не может быть null
    @NotNull
    private Location location; //Поле не может быть null

    /**
     * Constructor
     */

    public Person() {}

    /**
     * Constructor
     * @param name - the name of person
     * @param height - the height of person
     * @param color - the color of person's eyes
     * @param location - the location of person
     * @throws NullPointerException
     * @throws ValidationException
     */

    public Person(String name, Integer height, String color, Location location) throws NullPointerException, ValidationException {
        if(name == null || name.equals("") || height == null || color == null || location == null) {
            throw new NullPointerException("Check the values of variables. Name, height, color and location can be empty!");
        }
        if(height <= 0) {
            throw new ValidationException("The height is out of range! It must be more than 0");
        }
        this.name = name;
        this.height = height;
        setEyeColor(color);
        this.location = location;
    }

    public void setName(String name) throws NullPointerException, ValidationException {
        if(name == null || name.equals("")) {
            throw new NullPointerException("The name can not be empty!");
        }
        this.name = name;
    }

    public void setHeight(Integer height) throws NullPointerException, ValidationException {
        if(height == null) {
            throw new NullPointerException("The height can not be empty!");
        }
        if(height <= 0) {
            throw new ValidationException("The height is out of range! It must be more than 0");
        }
        this.height = height;
    }

    public void setEyeColor(String color) throws NullPointerException {
        if (color == null) {
            throw new NullPointerException("The color of eyes can not be empty!");
        }

        if (color.equals("RED")) {
            eyeColor = Color.RED;
        } else if (color.equals("BLUE")) {
            eyeColor = Color.BLUE;
        } else if (color.equals("ORANGE")) {
            eyeColor = Color.ORANGE;
        } else if (color.equals("WHITE")) {
            eyeColor = Color.WHITE;
        } else {
            eyeColor = Color.BROWN;
        }
    }

    public void setLocation(Location location) throws NullPointerException {
        if(location == null) {
            throw new NullPointerException("The location can not be empty!");
        }
        this.location = location;
    }

    public String getName() {return name;}
    public Integer getHeight() {return height;}
    public Color getEyeColor() {return  eyeColor;}
    public Location getLocation() {return location;}

    /**
     * Method which compares coordinates with each other.
     * @param anotherPerson - another coordinates.
     * @return
     */

    public int compareTo(Person anotherPerson) {
        if (height.equals(anotherPerson.height)) {
            return 0;
        }
        else if (height > anotherPerson.height) {
            return 1;
        }
        else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Person [Name = " + name + " height = " + height + ", eyeColor = " + eyeColor  + ", location = " + location + "] ";
    }
}
