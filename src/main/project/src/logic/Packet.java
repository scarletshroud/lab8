package src.logic;

import src.commands.Command;
import src.database.User;

import javax.jws.soap.SOAPBinding;
import java.io.Serializable;

public class Packet implements Serializable {
    private Command command;
    private User user;
    private Object argument;

    public Packet() {
        command = null;
        user = null;
        argument = null;
    }

    public Packet(Command command, User user, Object argument) {
        this.command = command;
        this.user = user;
        this.argument = argument;
    }

    public void wrap(Command command, User user) {
        this.command = command;
        this.user = user;
    }

    public void wrap(Command command, User user, Object argument) {
        this.command = command;
        this.user = user;
        this.argument = argument;
    }

    public Command getCommand() {
        return command;
    }

    public User getUser() {
        return user;
    }

    public Object getArgument() {
        return argument;
    }
}