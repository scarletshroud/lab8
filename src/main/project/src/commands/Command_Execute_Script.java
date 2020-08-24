package src.commands;

import src.client.Client;
import src.database.User;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;
import src.exceptions.BadNumberOfArgsException;
import src.exceptions.InvalidCommand;
import src.logic.*;
import src.server.Server;

import javax.swing.*;
import javax.xml.bind.ValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
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

    public ArrayList<Packet> execute(boolean authorized, User user, String path) {

        if (authorized) {

            packets.add(this.executeOnClient(true, user, path));

            boolean exit = false;

            try {
                for (String com : calls) {
                    if (com.equals(path)) {
                        exit = true;
                    }
                }
                if (exit) {
                    JOptionPane.showMessageDialog(null, "Warning! The danger of infinite recursion: " +
                            "the same script is called more that once\n");
                    return null;
                } else {
                    Input input = new Input(path);

                    while (input.hasNextLine()) {
                        String nextLine = input.readLine();
                        if (!nextLine.equals("")) {

                            calls.add(path);
                            try {
                                Command command = handler.pickCommand(nextLine);
                                if (nextLine.contains("execute_script")) {
                                    Command_Execute_Script com = (Command_Execute_Script) command;
                                     String[] args = nextLine.split(" ");
                                     ArrayList<Packet> answer = com.execute(true, user, args[1]);
                                } else {
                                    if (nextLine.contains("add")) {
                                        if (nextLine.split(" ").length == 2) {
                                            String[] args = new String[13];
                                            args[0] = nextLine.split(" ")[1];

                                            for (int i = 1; i < 13; i++) {
                                                if (input.hasNextLine()) {
                                                    nextLine = input.readLine();
                                                    args[i] = nextLine;
                                                } else {
                                                    break;
                                                }
                                            }

                                            try {
                                                Product product = new Product(args[0],
                                                        new Coordinates(
                                                                Float.parseFloat(args[1]),
                                                                Double.parseDouble(args[2])),
                                                        LocalDate.now(), Long.parseLong(args[3]),
                                                        args[4],
                                                        args[5],
                                                        new Person(
                                                                args[6],
                                                                Integer.parseInt(args[7]),
                                                                args[8],
                                                                new Location(
                                                                        Long.parseLong(args[9]),
                                                                        Long.parseLong(args[10]),
                                                                        Integer.parseInt(args[11]),
                                                                        args[12])));
                                                product.setHost(user.getLogin());
                                                packets.add(command.executeOnClient(true, user, product));
                                            } catch (ValidationException ex) {
                                                System.out.println(ex.getMessage());
                                            }

                                        }
                                    } else {
                                        packets.add(command.executeOnClient(true, user, nextLine));
                                    }
                                }
                            } catch (InvalidCommand ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }

                    input.closeFile();
                }
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "File not found! Enter the correct path to the file!\n");
                return null;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            return packets;
        }
        JOptionPane.showMessageDialog(null, "You must be logged in to continue working.\n");
        return null;
    }
}
