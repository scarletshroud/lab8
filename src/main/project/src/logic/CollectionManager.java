package src.logic;

import src.elements.*;

import javax.xml.bind.ValidationException;
import java.io.IOException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class which manages collection.
 */

public class CollectionManager {
    private TreeSet<Product> products;
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

    public CollectionManager() throws ValidationException, IOException {
        Input input = new Input();
        ParserXML parser = new ParserXML();
        products = new TreeSet<>();
        creationDate = LocalDateTime.now();
        history = new DefaultQueue(11);
        scanner = new Scanner(System.in);
        parser.Parse(input.readFile());
        for (int i = 1; i <= parser.getProductsNum(); i++) {
            Product product = new Product();
            Initializer.Initialize(product, parser.getValues());
            freeId = getFreeId();
            product.setId(freeId);
            products.add(product);
        }
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
        freeId = getFreeId();
        product.setId(freeId);
        products.add(product);
        modifyHistory("add");
        return "Product was successfully added to the collection.";
    }

    /**
     * Adds an element to collection if it is max
     * @throws ValidationException
     */

    public String addIfMax(Object object) {
        Product product = (Product) object;
        freeId = getFreeId();
        product.setId(freeId);
        modifyHistory("add_if_max");
        if (product.getPrice() > findMax()) {
            products.add(product);
            return "Product was successfully added to the collection.";
        } else {
            return "You are trying to add the product which isn't a max!";
        }
    }

    /**
     * Adds a new element to collection if it is min
     */

    public String addIfMin(Object object) {
        Product product = (Product) object;
        freeId = getFreeId();
        product.setId(freeId);
        modifyHistory("add_if_min");
        if (product.getPrice() < findMin()) {
                products.add(product);
            return "Product was successfully added to the collection.";
        } else {
            return "You are trying to add the product which isn't a min!";
        }
    }

    /**
     * Clears collection
     */

    public String clear() {
        products.clear();
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
     * Stops the app
     */

    public void finish() {
        exit = true;
        scanner.close();
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

    public String removeById(Object object) {
        Integer id = (Integer) object;
        products = products.stream()
                .filter(Product->Product.getId() != id)
                .collect(Collectors.toCollection(TreeSet::new));

        modifyHistory("remove_by_id");
        return "Element was successfully removed.";
    }

    /**
     * Saves collection to file
     * @throws IOException
     */

    public void save() {
        try {
            Output output = new Output();
            output.writeLine("<xml -version 8.0>");
            output.writeLine("<Class>");
            for (Product product : products) {
                output.writeLine("  <Product>");
                output.writeLine("    <Name>" + product.getName() + "<\\Name>");
                output.writeLine("    <Coordinates>");
                output.writeLine("      <X>" + product.getCoordinates().getX() + "<\\X>");
                output.writeLine("      <Y>" + product.getCoordinates().getY() + "<\\Y>");
                output.writeLine("    <\\Coordinates>");
                output.writeLine("    <CreationDate>" + product.getCreationDate() + "<\\CreationDate>");
                output.writeLine("    <Price>" + product.getPrice() + "<\\Price>");
                output.writeLine("    <partNumber>" + product.getPartNumber() + "<\\partNumber>");
                output.writeLine("    <UnitOfMeasure>" + product.getUnitOfMeasure() + "<\\UnitOfMeasure>");
                output.writeLine("    <Person>");
                output.writeLine("      <Name>" + product.getOwner().getName() + "<\\Name>");
                output.writeLine("      <Height>" + product.getOwner().getHeight() + "<\\Height>");
                output.writeLine("      <eyeColor>" + product.getOwner().getEyeColor() + "<\\eyeColor>");
                output.writeLine("      <Location>");
                output.writeLine("        <X>" + product.getOwner().getLocation().getX() + "<\\X>");
                output.writeLine("        <Y>" + product.getOwner().getLocation().getY() + "<\\Y>");
                output.writeLine("        <Z>" + product.getOwner().getLocation().getZ() + "<\\Z>");
                output.writeLine("        <Name>" + product.getOwner().getLocation().getName() + "<\\Name>");
                output.writeLine("      <\\Location>");
                output.writeLine("    <\\Person>");
                output.writeLine("  <\\Product>");
            }
            output.writeLine("<\\Class>");
            output.closeWriter();
            System.out.println("Saving is successful!");
            modifyHistory("save");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Shows collection in string presentation
     */

    public String show() {
        String result = new String();
        for(Product p : products){
            result += p.toString() + "\n";
        }
        modifyHistory("show");
        return result;
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

    public String updateId(Object object) {
        Object[] objects = (Object[]) object;
        Integer id = (Integer) objects[0];
        String name = (String) objects[1];

        if (isIdBusy(id)) {
         return "Impossible to update the id. This id is already used.";
        } else {
            products.stream()
                    .filter(p -> p.getName().equals(name))
                    .forEach((p) -> p.setId(id));

            modifyHistory("update_id");
            return "The id of the element was successfully updated.";
        }
    }

    public boolean getExit() {
        return exit;
    }

}