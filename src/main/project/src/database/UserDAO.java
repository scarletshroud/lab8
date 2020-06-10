package src.database;

import com.sun.istack.internal.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DAO <User, String> {

    private Connection connection;

    UserDAO(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(@NotNull final User user) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.INSERT.QUERY)) {
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public User read(@NotNull final String login) {
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
    public void update(@NotNull final User user) {
        try(PreparedStatement statement = connection.prepareStatement(sqlQueries.UPDATE.QUERY)) {
            statement.setString(1, user.getPassword());
            statement.setInt(2, user.getId());
            statement.executeQuery().next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(@NotNull final User user) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQueries.DELETE.QUERY)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeQuery().next();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    enum sqlQueries {
        INSERT("INSERT INTO users (id, login, password) VALUES (DEFAULT, (?), (?)) RETURNING id"),
        GET("SELECT u.id, u.login, u.password FROM users"),
        UPDATE("UPDATE users SET password = (?) WHERE id = (?) RETURNING id"),
        DELETE("DELETE FROM users WHERE id = (?) AND login = (?) AND password = (?) RETURNING id");

        String QUERY;

        sqlQueries(String query) {
            this.QUERY = query;
        }
    }
}
