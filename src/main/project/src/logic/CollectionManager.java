package src.logic;

import src.database.DBManager;
import src.database.User;
import src.elements.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class which manages collection.
 */

public class CollectionManager {
    private TreeSet<Product> products;
    private DBManager dbManager;
    private LocalDateTime creationDate;
    private DefaultQueue history;
    private Scanner scanner;
    private boolean exit = false;

    private static int freeId;

    /**
     * Constructor
     * @throws ValidationException
     * @throws IOException
     */

    public CollectionManager(DBManager dbManager) throws ValidationException, IOException {

        products = new TreeSet<>();
        creationDate = LocalDateTime.now();
        history = new DefaultQueue(11);
        scanner = new Scanner(System.in);
        this.dbManager = dbManager;
        products.addAll(dbManager.readAllProducts());
    }

    /**
     * Modifies history of used src.commands.
     * @param command - the next command
     */

    private void modifyHistory(String command){
        history.insert(command);
    }

    /**
     * Finds a max price of src.elements in collection
     * return the maximum value
     */

    private int getFreeId() {
        if (!products.isEmpty()) {
            Product max = products.stream()
                    .max(Comparator.comparing(Product::getId))
                    .get();
            return max.getId() + 1;
        } else {
            return 1;
        }
    }

    private boolean isIdBusy(int id) {
        if (!products.isEmpty()) {
            return products.stream().anyMatch(p->p.getId() == id);
        } else {
            return false;
        }
    }

    private Long findMax() {
        Product max = products.stream()
                .max(Comparator.comparing(Product::getPrice))
                .get();
        /*
        for (Product p: products) {
            if (p.getPrice() > max) {
                max = p.getPrice();
            }
        } */
        return max.getPrice();
    }

    /**
     * Finds a min price of src.elements in collection
     * @return the minimal value
     */

    private long findMin() {
        PriceComparator comparator = new PriceComparator();
        Product min = products.stream()
                .min(Comparator.comparing(Product::getPrice))
                .get();
      /*  for (Product p: products) {
            if (p.getPrice() < min) {
                min = p.getPrice();
            }
        }  */
        return min.getPrice();
    }

    /**
     * Adds a new element to collection
     */

    public String add(Object object) {
        Product product = (Product) object;
        modifyHistory("add");
        int id = dbManager.createProduct(product);
        if (id  != -1) {
            product.setId(id);
            products.add(product);
            return "Product was successfully added to the collection.";
        } else {
            return "There are some problems with adding a product to collection.";
        }
    }

    /**
     * Adds an element to collection if it is max
     * @throws ValidationException
     */

    public String addIfMax(Object object) {
        Product product = (Product) object;
        modifyHistory("add_if_max");
        if (product.getPrice() > findMax()) {
            int id = dbManager.createProduct(product);
            if (id != -1) {
                products.add(product);
                return "Product was successfully added to the collection.";
            } else {
                return "There are some problems with adding a product to collection.";
            }
        } else {
            return "You are trying to add the product which isn't a max!";
        }
    }

    /**
     * Adds a new element to collection if it is min
     */

    public String addIfMin(Object object) {
        Product product = (Product) object;
        modifyHistory("add_if_min");
        if (product.getPrice() < findMin()) {
            int id = dbManager.createProduct(product);
            if (id != -1) {
                products.add(product);
                return "Product was successfully added to the collection.";
            } else {
                return "There are some problems with adding a product to collection.";
            }
        }
        return "You are trying to add the product which isn't a min!";
    }

    /**
     * Clears collection
     */

    public String clear(User user) {

        for (Product p : products) {
            if (p.getHost().equals(user.getLogin())) {
                products.remove(p);
                dbManager.deleteProduct(p.getId());
            }
        }

        modifyHistory("clear");
        return "The collection was cleared.";
    }

    /**
     * Just for saving in history command execute script
     */

    public String executeScript() {
        modifyHistory("execute_script");
        return "A new script was started to execute";
    }

    /**
     * Filters collection by unit of measure
     */

    public String filterByUnitOfMeasure(Object object) {
        String unitOfMeasure = (String) object;
        String result = "The result of filtering by unit of measure:\n";

        List<Product> res = products.stream()
                .filter(Product -> unitOfMeasure.equals(Product.getUnitOfMeasure().getUnit()))
                .collect(Collectors.toList());

        for (Product p : res) {
            result += p.getName() + "\n";
        }

        modifyHistory("filter_by_unit_of_measure");
        return result;
    }

    /**
     * Shows the list of available src.commands
     */

    public String help() {
        modifyHistory("help");
        return "//// HELP //// " +
                "\ninfo : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" +
                "\nshow : вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                "\nadd {element} : добавить новый элемент в коллекцию" +
                "\nupdate id {element} : обновить значение элемента коллекции, id которого равен заданному" +
                "\nremove_by_id id : удалить элемент из коллекции по его id" +
                "\nclear : очистить коллекцию" +
                "\nsave : сохранить коллекцию в файл" +
                "\nexecute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме" +
                "\nexit : завершить программу (без сохранения в файл)" +
                "\nadd_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
                "\nadd_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции" +
                "\nhistory : вывести последние 11 команд (без их аргументов)" +
                "\nfilter_by_unit_of_measure unitOfMeasure : вывести элементы, значение поля unitOfMeasure которых равно заданному" +
                "\nprint_unique_part_number partNumber : вывести уникальные значения поля partNumber" +
                "\nprint_field_descending_owner owner : вывести значения поля owner в порядке убывания";
    }

    /**
     * Shows history of last used src.commands
     */

    public String history() {
        String result = "The history of your last used src.commands:\n";
        for(int i = 0; i < history.getSize(); i++) {
            result += history.getElement(i) + "\n";
        }
        modifyHistory("history");
        return result;
    }

    /**
     * Shows info about collection
     */

    public String info() {
        modifyHistory("info");
        try {
            Field treeSetField = CollectionManager.class.getDeclaredField("products");
            String treeSetType = treeSetField.getGenericType().getTypeName();
            if (!products.isEmpty()) {
                return "Type: " + products.getClass().getName() + "<" + treeSetType + ">" + "\nCreation Date" + creationDate + "\nSize: " + products.size();
            } else {
                return "Type can not be defined because collection is empty! " + "\nCreation Date" + creationDate + "\nSize: " + products.size();
            }
        } catch (NoSuchFieldException ex) {
            return "Problem with general class. Can not find type of class!";
        }
    }

    /**
     * Prints owners in decreasing order
     */

    public String printFieldDescendingOwner() {
        ArrayList<Person> ownersList = new ArrayList<>();
        String result = "The owners:\n";

        for (Product product: products) {
            ownersList.add(product.getOwner());
        }

        List<Person> answ = ownersList.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        for (Person p : ownersList) {
            result += p.getName() + "\n";
        }

        modifyHistory("print_field_descending_owner");
        return result;
    }

    /**
     * Removes an element by id
     */

    public String removeById(User user, Object object) {
        Integer id = (Integer) object;

        modifyHistory("remove_by_id");

        for (Product p : products) {
            if (p.getId() == id) {
                if (p.getHost().equals(user.getLogin())) {
                    products.remove(p);
                    dbManager.deleteProduct(id);
                    return "Element was successfully removed.";
                }
                else {
                    return "You don't have a permission to change this element!";
                }
            }
        }
        return "The element with this id wasn't found.";
    }

    /**
     * Shows collection in string presentation
     */

    public String show() {
        String result = new String();

        modifyHistory("show");

        if (!products.isEmpty()) {
            for (Product p : products) {
                result += p.toString() + "\n";
            }
            return result;
        }

        return "Collection is empty.";
    }

    /**
     * Prints unique numbers of parts
     */

    public String printUniquePartNumber() {
        ArrayList<String> partNumbers = new ArrayList<>();
        String result = new String();
        for(Product product: products) {
            partNumbers.add(product.getPartNumber());
        }
        HashSet <String> uniqueNumbers = new HashSet<>(partNumbers);

        modifyHistory("print_unique_part_number");

        for (String s : uniqueNumbers) {
            result += s + "\n";
        }
        return result;
    }

    /**
     * Updates id of element
     */

    public String updateId(User user, Object object) {
        Object[] objects = (Object[]) object;
        Integer id = (Integer) objects[0];
        String name = (String) objects[1];

        modifyHistory("update_id");

        for (Product p : products) {
            if (p.getName().equals(name) &&  !isIdBusy(id)) {
                if (p.getHost().equals(user.getLogin())) {
                    p.setId(id);

                    Product product = new Product();
                    product.setId(id);
                    product.setName(name);

                    dbManager.updateProduct(product);

                    return "The element's id was successfully updated!";
                }
                return "You don't have a permission to change this element!";
            }
        }

        return "This id is busy.";

    }

    public boolean getExit() {
        return exit;
    }

}