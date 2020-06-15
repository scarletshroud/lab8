package src.database;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.server.Server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO <User, String> {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    private Connection connection;

    UserDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    synchronized public int create(@NotNull final User user) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.INSERT.QUERY)) {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.executeQuery();
        final ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    synchronized public int create(@NotNull final String login, @NotNull final String pass) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.INSERT.QUERY)) {
            statement.setString(1, login);
            statement.setString(2, pass);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    @Override
    synchronized public User read(@NotNull final String login) {
        User user = new User();
        user.setId(-1);

        try(PreparedStatement statement = connection.prepareStatement(sqlQueries.GET.QUERY)) {
            statement.setString(1, login);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setLogin(login);
                user.setPassword(rs.getString("password"));
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    @Override
    synchronized public void update(@NotNull final User user) {
        try(PreparedStatement statement = connection.prepareStatement(sqlQueries.UPDATE.QUERY)) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            statement.executeQuery().next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
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

    synchronized public boolean isAvailable(@NotNull final String login, @NotNull final String pass) {
        logger.info("isAvailable");
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.GET.QUERY)) {
            statement.setString(1, login);
            statement.setString(2, pass);
            final ResultSet rs = statement.executeQuery();
            return (rs.next());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    enum sqlQueries {
        INSERT("INSERT INTO users (id, login, password) VALUES (DEFAULT, (?), (?)) RETURNING id"),
        GET("SELECT * FROM users WHERE  login IN (?) AND password IN (?)"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id"),
        DELETE("DELETE FROM users WHERE id = (?)");

        String QUERY;

        sqlQueries(String query) {
            this.QUERY = query;
        }
    }
}
