package src.commands;

import src.client.Client;
import src.database.User;
import src.exceptions.BadNumberOfArgsException;
import src.exceptions.InvalidCommand;
import src.logic.*;
import src.server.Server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class of command Execute Script.
 * This command execute file with script.
 */

public class Command_Execute_Script extends Command implements Serializable {
    private CommandHandler handler;
    private static ArrayList<String> calls;
    private static ArrayList<Packet> packets;

    /**
     * Constructor
     * @param handler - the object of CommandHandler
     */

    public Command_Execute_Script(CommandHandler handler) {
        this.handler = handler;
        calls = new ArrayList<>();
        packets = new ArrayList<>();
    }

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 1;
    }

    /**
     * This method executes script.
     * @param server -the manager of collection
     */

    @Override
    public ServerPacket executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return new ServerPacket(null, server.getCollectionManager().executeScript(), true, true);
        }
        return new ServerPacket(null, "You don't have rights to interact with collection!\n", false, true);
    }

    public ArrayList<Packet> execute(boolean authorized, User user, String userInput) {

        if (authorized) {

            String[] args = userInput.split(" ");
            String path = args[1];
            packets.add(this.executeOnClient(true, user, path));

            boolean exit = false;

            try {
                for (String com : calls) {
                    if (com.equals(path)) {
                        exit = true;
                    }
                }
                if (exit) {
                    System.out.println("Warning! The danger of infinite recursion: " +
                            "the same script is called more that once\n");
                    return null;
                } else {
                    Input input = new Input(path);
                    String nextLine;
                    while (input.hasNextLine()) {
                        nextLine = input.readLine();

                        if (exit) {
                            exit = false;
                            break;
                        }
                        calls.add(path);
                        try {
                            Command command = handler.pickCommand(nextLine);
                            if (nextLine.contains("execute_script")) {
                                Command_Execute_Script com = (Command_Execute_Script) command;
                                ArrayList<Packet> answer = com.execute(true, user, nextLine);
                            } else {
                                packets.add(command.executeOnClient(true, user, nextLine));
                            }
                        } catch (InvalidCommand ex) {
                            System.out.println(ex.getMessage());
                        }
                    }

                    input.closeFile();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found! Enter the correct path to the file!\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return packets;
        }
        System.out.println("You must be logged in to continue working.\n");
        return null;
    }
}
