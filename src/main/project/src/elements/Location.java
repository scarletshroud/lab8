package src.elements;


import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.xml.bind.ValidationException;
import java.io.Serializable;

/**
 * Class of location of product.
 */

public class Location implements Comparable<Location>, Serializable {
    @NotNull
    private Long x;
    @NotNull
    private Long y;
    @Nullable
    private int z;
    @NotNull
    private String name; //Длина строки не должна быть больше 881

    /**
     * Constructor
     */

    public Location(){}

    /**
     * Constructor
     * @param x - the coordinate x
     * @param y - the coordinate y
     * @param z - the coordinate z
     * @param name - the name of location
     * @throws NullPointerException
     * @throws ValidationException
     */

    public Location(Long x, Long y, int z, String name) throws NullPointerException, ValidationException {
        if (x == null || y == null) {
            throw new NullPointerException("Check the variables. The coordinate x or coordinate y can not be empty!");
        }
        if (name.length() > 881) {
            throw new ValidationException("The length of name is out of range! It must be less than 881 characters.");
        }
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public void setX(Long x) throws NullPointerException {
        if (x == null) {
            throw new NullPointerException("The x can not be empty!'");
        }
        this.x = x;
    }

    public void setY(Long y) throws NullPointerException {
        if (y == null)
            throw new NullPointerException("The y can not be empty!");
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setName(String name) throws ValidationException {
        if (name.length() > 881) {
            throw new ValidationException("The length of name is out of range! It must be less than 881 characters.");
        }
        this.name = name;
    }

    public Long getX() {return x;}
    public Long getY() {return y;}
    public int getZ() {return z;}
    public String getName() {return name;}

    /**
     * Method which compares coordinates with each other.
     * @param anotherLocation - another coordinates.
     * @return
     */

    public int compareTo(Location anotherLocation){
        if (x.equals(anotherLocation.x)) {
            return 0;
        } else if (x > anotherLocation.x) {
            return 1;
        }
        return -1;
    }


    @Override
    public String toString() {
        return "Location [Name = " + name + " x = " + x + ", y = " + y  + ", z = " + z + "] ";
    }
}
