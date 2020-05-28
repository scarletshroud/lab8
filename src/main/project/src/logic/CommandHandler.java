package src.logic;

import src.commands.Command;
import src.exceptions.InvalidCommand;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class of CommandHandler.
 * This class works with classes of src.commands.
 */

public class CommandHandler implements Serializable {

    private HashMap<String, Command> commandMap = new HashMap<>();

    /**
     * Method for register a new command
     * @param commandName - the name of command
     * @param command - the class of command
     */

    public void register(String commandName, Command command) {
        commandMap.put(commandName, command);
    }

    /**
     * Method which executes command
     * @param commandName - the name of command
     * @throws InvalidCommand
     */

    public Command pickCommand(String commandName) throws InvalidCommand {
        if(commandName.equals("")){
            throw new InvalidCommand("Trying to call invalid command! See more info about available src.commands. \"info\"");
        }

        String[] input = commandName.split(" ");

        Command command = commandMap.get(input[0]);
        if (command == null) {
            throw new InvalidCommand("Trying to call invalid command! " + "\"" + commandName + "\"" + " See more info about available src.commands. \"info\"");
        }

        String[] args = new String[input.length-1];
        System.arraycopy(input, 1, args, 0, input.length-1);

        if (command.validateArgs(args)) {
            return command;
        }
        else {
            System.out.println("You are trying to call a command with wrong arguments!");
            return null;
        }
    }
}