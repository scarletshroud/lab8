package src.database;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;
import src.server.Server;

import javax.xml.bind.ValidationException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProductDAO implements DAO<Product, String> {

    private Connection connection;
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);

    ProductDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    synchronized public int create(@NotNull final Product product) {
        try(PreparedStatement statement = connection.prepareStatement(sqlQueries.INSERT.QUERY)) {
            statement.setString(1, product.getName());
            statement.setFloat(2, product.getCoordinates().getX());
            statement.setDouble(3, product.getCoordinates().getY());
            statement.setDate(4, Date.valueOf(product.getCreationDate().toString()));
            statement.setLong(5, product.getPrice());
            statement.setString(6, product.getPartNumber());
            statement.setString(7, product.getUnitOfMeasure().toString());
            statement.setString(8, product.getOwner().getName());
            statement.setInt(9, product.getOwner().getHeight());
            statement.setString(10, product.getOwner().getEyeColor().toString());
            statement.setString(11, product.getOwner().getLocation().getName());
            statement.setLong(12, product.getOwner().getLocation().getX());
            statement.setLong(13, product.getOwner().getLocation().getY());
            statement.setInt(14, product.getOwner().getLocation().getZ());
            statement.setString(15, product.getHost());
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    @Override
    synchronized public Product read(@NotNull final String name) {
        Product product = new Product();
        try(PreparedStatement statement = connection.prepareStatement(sqlQueries.GET.QUERY)) {
            statement.setString(1, name);
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return product;
    }

    synchronized public ArrayList<Product> readAll() {
        ArrayList<Product> products = new ArrayList<>();

        try(PreparedStatement statement = connection.prepareStatement(sqlQueries.GET_ALL.QUERY)) {
            final ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                try {
                Product product = new Product(rs.getString("name"),
                        new Coordinates(rs.getFloat("coordinate_x"), rs.getDouble("coordinate_y")),
                        LocalDate.parse(rs.getString("creation_date")), rs.getLong("price"),
                        rs.getString("part_number"),  rs.getString("unit_of_measure"),
                        new Person(rs.getString("person_name"), rs.getInt("person_height"), rs.getString("person_eyeColor"),
                        new Location(rs.getLong("location_x"), rs.getLong("location_y"), rs.getInt("location_z"), rs.getString("location_name"))));
                product.setId(rs.getInt("id"));
                product.setHost(rs.getString("creator"));
                products.add(product);
                } catch(ValidationException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            rs.close();
        } catch (SQLException ex) {
          //  System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        return products;
    }

    @Override
    synchronized public void delete(@NotNull final int id) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.DELETE.QUERY)) {
            statement.setInt(1, id);
            statement.executeQuery().next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    synchronized public void update(@NotNull final Product product) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.UPDATE.QUERY)) {
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.executeQuery();
        } catch (SQLException ex) {
            logger.info(ex.getMessage());
        }
    }

    enum sqlQueries {
        INSERT("INSERT INTO products (id, name, coordinate_x, coordinate_y, creation_date, price, part_number, unit_of_measure, person_name, person_height, person_eyeColor, location_name, location_x, location_y, location_z, creator) VALUES (DEFAULT, (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?), (?)) RETURNING id"),
        GET("SELECT p.id, p.name, p.coordinate_x, p.coordinate_y, p.creation_date, p.price, p.part_number, p.unit_of_measure, p.person_name, p.person_height, p.person_eyeColor, p.location_name, p.location_x, p.location_y, p.location_z, host FROM products"),
        UPDATE("UPDATE products SET id = (?) WHERE name = (?)"),
        DELETE("DELETE FROM products WHERE id = (?)"),
        GET_ALL("SELECT * FROM products");

        String QUERY;

        sqlQueries(String query) {
            this.QUERY = query;
        }
    }
}
