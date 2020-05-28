package src.elements;

import com.sun.istack.internal.NotNull;
import javax.xml.bind.ValidationException;
import java.io.Serializable;

/**
 * Class of coordinates of product.
 */

public class Coordinates implements Comparable<Coordinates>, Serializable {
    @NotNull
    private Float x; //Максимальное значение поля: 412, Поле не может быть null
    @NotNull
    private Double y; //Максимальное значение поля: 423, Поле не может быть null
    private final Float MAX_X = 412f;
    private final Double MAX_Y = 423d;

    /**
     * Constructor.
     */

    public Coordinates() {}

    /**
     * Constructor
     * @param x - the coordinate x
     * @param y - the coordinate y
     * @throws NullPointerException
     * @throws ValidationException
     */

    public Coordinates(Float x, Double y) throws NullPointerException, ValidationException {
        if (x == null || y == null)
            throw new NullPointerException();
        if (x > MAX_X || y > MAX_Y)
            throw new ValidationException("The x or y is out of range! Check the values. The coordinate x must be less than " + MAX_X+1 +
                    "The coordinate y must be less than " + MAX_Y + 1);
        this.x = x;
        this.y = y;
    }

    public void setX(Float x) throws NullPointerException, ValidationException {
        if (x == null)
            throw new NullPointerException("The x can not be empty!");
        if (x > MAX_X)
            throw new ValidationException("The x is out of range! The coordinate x must be less than " + MAX_X + 1);
        this.x = x;
    }

    public void setY(Double y) throws NullPointerException, ValidationException {
        if (y == null)
            throw new NullPointerException("The y can not be empty!");
        if (y > MAX_Y)
            throw new ValidationException("The  y is out of range! The coordinate y must be less than " + MAX_Y + 1);
        this.y = y;
    }

    public Float getX() {return x;}
    public Double getY() {return y;}

    /**
     * Method which compares coordinates with each other.
     * @param anotherCoordinates - another coordinates.
     * @return
     */

    @Override
    public int compareTo(Coordinates anotherCoordinates){
        if (x.equals(anotherCoordinates.x)) {
            return 0;
        }
        else if (x > anotherCoordinates.x) {
            return 1;
        }
        else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Coordinates [x = " + x + ", y = " + y  + ", MAX_X = " + MAX_X + ", MAX_Y = " + MAX_Y + "] ";
    }
}