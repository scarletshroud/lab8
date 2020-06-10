package src.elements;

import com.sun.istack.internal.NotNull;

import javax.xml.bind.ValidationException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class of product.
 */

public class Product implements Comparable<Product>, Serializable {
    private int id;//Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    private String name; //Строка не может быть пустой
    @NotNull
    private Coordinates coordinates;
    @NotNull
    private LocalDate creationDate; //Значение этого поля должно генерироваться автоматически
    @NotNull
    private Long price; //Значение поля должно быть больше 0
    @NotNull
    private String partNumber; //Длина строки не должна быть больше 75, Строка не может быть пустой
    @NotNull
    private UnitOfMeasure unitOfMeasure;
    @NotNull
    private Person owner;

    /**
     * Constructor
     */

    public Product(){
        id = 0;
        creationDate = LocalDate.now();
    }

    /**
     * Constructor
     * @param name - the name of product
     * @param coordinates - the coordinates of product
     * @param price - the price of product
     * @param partNumber - the number of parts of product
     * @param unitOfMeasure - the unit of measure of product
     * @param owner - the owner of product
     * @throws NullPointerException
     * @throws ValidationException
     */

    public Product(String name, Coordinates coordinates, LocalDate creationDate, Long price, String partNumber, String unitOfMeasure, Person owner) throws NullPointerException, ValidationException {
        if (name == null || name.equals("") || creationDate == null || coordinates == null || price == null || partNumber == null || partNumber.equals("") || unitOfMeasure == null || owner == null) {
            throw new NullPointerException();
        }
        if(price <= 0) {
            throw new ValidationException("The price is out of range! It must be more than 0");
        }
        if(partNumber.length() > 75) {
            throw new ValidationException("The number of parts is out of range! It must be less than 76");
        }

        id = 0;
        this.name = name;
        this.creationDate = creationDate;
        this.coordinates = coordinates;
        this.price = price;
        this.partNumber = partNumber;

        if (unitOfMeasure.equals("PCS")) {
            this.unitOfMeasure = UnitOfMeasure.PCS;
        } else if (unitOfMeasure.equals("GRAMS")) {
            this.unitOfMeasure = UnitOfMeasure.GRAMS;
        } else if (unitOfMeasure.equals("MILLLITERS")) {
            this.unitOfMeasure = UnitOfMeasure.MILLILITERS;
        }

        this.owner = owner;
    }

    /**
     * Updates id of product
     */

    public void setId(int freeId) {
        id = freeId;
    }

    public void setName(String name) throws NullPointerException {
        if (name == null || name.equals("")) {
            throw new NullPointerException("The name can not be empty!'");
        }
        this.name = name;
    }

    public void setCoordinates(Coordinates coordinates) throws NullPointerException {
        if(coordinates == null) {
            throw new NullPointerException("The coordinates can not be empty!");
        }
        this.coordinates = coordinates;
    }

    public void setCreationDate(LocalDate creationDate) throws NullPointerException {
        if (creationDate == null) {
            throw new NullPointerException("The creation date can not be empty!");
        }
        this.creationDate = creationDate;
    }

    public void setPrice(Long price) throws NullPointerException, ValidationException {
        if(price == null) {
            throw new NullPointerException("The price can not be empty");
        }
        if(price <= 0) {
            throw new ValidationException("The price is out of range! It must be more than 0");
        }
        this.price = price;
    }

    public void setPartNumber(String partNumber) throws NullPointerException, ValidationException {
        if(partNumber == null) {
            throw new NullPointerException("The number of parts can not be empty!");
        }
        if(partNumber.length() > 75) {
            throw new ValidationException("The number of parts is out of range! It must be less than 76");
        }
        this.partNumber = partNumber;
    }

    public void setUnitOfMeasure(String unitOfMeasure) throws NullPointerException {
        if(unitOfMeasure == null) {
            throw new NullPointerException("The unit of measure can not be null!");
        }
        if (unitOfMeasure.equals("PCS")) {
            this.unitOfMeasure = UnitOfMeasure.PCS;
        } else if (unitOfMeasure.equals("GRAMS")) {
            this.unitOfMeasure = UnitOfMeasure.GRAMS;
        } else {
            this.unitOfMeasure = UnitOfMeasure.MILLILITERS;
        }
    }

    public void setOwner(Person owner) throws NullPointerException {
        if(owner == null) {
            throw new NullPointerException("Null Equals Exception: 'The variable owner is null!'");
        }
        this.owner = owner;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public Coordinates getCoordinates() {return coordinates;}
    public LocalDate getCreationDate() {return creationDate;}
    public Long getPrice() {return price;}
    public String getPartNumber() { return partNumber;}
    public UnitOfMeasure getUnitOfMeasure() {return unitOfMeasure;}
    public Person getOwner() {return owner;}

    /**
     * Method which compares coordinates with each other.
     * @param anotherProduct - another coordinates.
     * @return
     */

    @Override
    public int compareTo(Product anotherProduct){
        if (id == anotherProduct.id) {
            return 0;
        } else if (id > anotherProduct.id) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "Product [Id = " + id +  ", Name = " + name + " coordinates = " + coordinates
                + ", creationDate = " + creationDate  + ", price = " + price
                + ", partNumber = " + partNumber + ", unitOfMeasure = " + unitOfMeasure + ", owner =  " + owner + "] ";
    }
}
