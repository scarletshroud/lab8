package src.database;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String login;
    private String password;

    public User() {}

    public User(@NotNull final String login, @NotNull final String password) {
        this.login = login;
        this.password = password;
    }

    public void setId(@NotNull final int id) {
        this.id = id;
    }

    public void setLogin(@NotNull final String login) {
        this.login = login;
    }

    public void setPassword(@NotNull final String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }
}
