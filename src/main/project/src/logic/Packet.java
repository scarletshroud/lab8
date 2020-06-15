package src.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.commands.Command;
import src.database.User;
import src.server.Server;

import javax.jws.soap.SOAPBinding;
import java.io.Serializable;

public class Packet implements Serializable {
    private Command command;
    private User user;
    private Object argument;
    private static final Logger logger = LoggerFactory.getLogger(Server.class);

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
        logger.info("getCommand"); return command;
    }

    public User getUser() {
        logger.info("getUser");
        return user;
    }

    public Object getArgument() {
        logger.info("getArgs");
        return argument;
    }
}