package src.database;

import com.sun.istack.internal.NotNull;
import src.elements.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBManager {

    private String DB_URL = "jdbc:postgresql://127.0.0.1:5432/lab7";
    private String USER = "postgres";
    private String PASS = "B7OQdoHH";
    private Connection connection;
    private ProductDAO productDAO;
    private UserDAO userDAO;

    public DBManager() {
        connection = getDBConnection();
        productDAO = new ProductDAO(connection);
        userDAO = new UserDAO(connection);
    }

    public DBManager(String DB_URL, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.USER = USER;
        this.PASS = PASS;
        connection = getDBConnection();
    }

    private Connection getDBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
        }
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            System.out.println("Unable to connect to data base");
        }
        return null;
    }

    public void createUser(@NotNull final User user) {
        userDAO.create(user);
    }

    public void createProduct(@NotNull final Product product) {
        productDAO.create(product);
    }

    public User readUser(@NotNull final String login) {
       return userDAO.read(login);
    }

    public Product readProduct(@NotNull final String name) {
       return productDAO.read(name);
    }

    public ArrayList<Product> readAllProducts() {
      return productDAO.readAll();
    }

    public void updateUser(@NotNull final User user) {
        userDAO.update(user);
    }

    public void updateProduct(@NotNull final Product product) {
        productDAO.update(product);
    }

    public void deleteUser(@NotNull final User user) {
        userDAO.delete(user);
    }

    public void deleteProduct(@NotNull final Product product) {
        productDAO.delete(product);
    }

    public void clearProducts() {
        productDAO.clear();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
