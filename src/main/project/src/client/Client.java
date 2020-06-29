package src.client;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.client.gui.AuthorizationWindow;
import src.database.User;
import src.exceptions.InvalidCommand;
import src.logic.CommandHandler;
import src.commands.*;
import src.logic.Packet;
import src.logic.SerializationManager;
import src.logic.ServerPacket;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client implements Runnable {

    public static Client client;

    private CommandHandler commandHandler;
    private DataInputStream ois;
    private DataOutputStream oos;

    private static AuthorizationWindow authorizationWindow;

    private final String host;
    private final int port;

    private final int BUFFER_SIZE = 4096;
    private Boolean authorized;

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    private static User user;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        ois = null;
        oos = null;

        authorized = false;

        commandHandler = new CommandHandler();
        user = new User();
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 29666);
        client.run();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            System.err.println("Look and feel not set.");
        }
        authorizationWindow = new AuthorizationWindow(client);
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(host, port);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            oos = new DataOutputStream(socket.getOutputStream());
            ois = new DataInputStream(socket.getInputStream());

            logger.info("Client connected to socket");
            logger.info("Client writing channel = oos & reading channel = ois initialized.");

            registerCommands();

        } catch (IOException ex) {
            System.out.println("Server is not working. Try to connect later.");
            logger.info("Unable to connect to server.");
        }
    }

    public void close(Socket socket, DataInputStream ois, DataOutputStream oos) {
        try {
            oos.close();
            ois.close();
            socket.close();
            logger.info("Closing the connection...");
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.info("Some problems with closing the socket!");
        }
    }

    public boolean handleRequest(String userInput) throws IOException {

        try {

            Command command = commandHandler.pickCommand(userInput);

            if (command != null) {

                String[] args = createArgs(userInput);

                if (userInput.contains("exit")) {
                    logger.info("Client stopped working with server.");
                    return true;
                }

                if (userInput.contains("execute_script")) {
                    Command_Execute_Script com = (Command_Execute_Script) command;
                    ArrayList<Packet> packets = com.execute(authorized, user, userInput);
                    if (packets != null) {
                        for (Packet p : packets) {
                            sendRequest(p);
                        }
                    }
                } else {
                    Packet packet = command.executeOnClient(authorized, user, args);
                    return sendRequest(packet);
                }
            }

        } catch (InvalidCommand ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public boolean sendRequest(Packet packet) {
        try {

            if (packet != null) {

                byte[] message = SerializationManager.serializeObject(packet);
                oos.write(message);
                oos.flush();
                logger.info("Client sent message to server.");

                logger.info("Client sent message & start waiting for data from server...");
            }
        } catch (IOException ex) {
            logger.info("Client lost connection with server. Stopping work.");
            System.out.println("At the moment server is not working");
            return true;
        }
        return false;
    }

    public ServerPacket acceptAnswer() {
        try {

            while (ois.available() <= 0) { }

            logger.info("Trying to read data...");
            byte[] bytes = new byte[BUFFER_SIZE];
            ois.read(bytes);
            return SerializationManager.deserializeObject(bytes);

        } catch (IOException ex) {
            logger.info("Some problems with accepting the answer.");
            return null;
        }
    }

    private String[] createArgs(@NotNull String input) {
        String[] args = input.split(" ");
        String[] argsToSend = new String[args.length-1];
        System.arraycopy(args, 1, argsToSend, 0, args.length-1);
        return argsToSend;
    }

    private void registerCommands() {
        commandHandler.register("login", new Command_Login());
        commandHandler.register("register", new Command_Register());
        commandHandler.register("help", new Command_Help());
        commandHandler.register("info", new Command_Info());
        commandHandler.register("show", new Command_Show());
        commandHandler.register("add", new Command_Add());
        commandHandler.register("update_id", new Command_Update_Id());
        commandHandler.register("remove_by_id", new Command_Remove_By_Id());
        commandHandler.register("clear", new Command_Clear());
        commandHandler.register("execute_script", new Command_Execute_Script(commandHandler));
        commandHandler.register("exit", new Command_Exit());
        commandHandler.register("add_if_max", new Command_Add_If_Max());
        commandHandler.register("add_if_min", new Command_Add_If_Min());
        commandHandler.register("history", new Command_History());
        commandHandler.register("filter_by_unit_of_measure", new Command_Filter_By_Unit_Of_Measure());
        commandHandler.register("print_unique_part_number", new Command_Print_Unique_Part_Number());
        commandHandler.register("print_field_descending_owner", new Command_Print_Field_Descending_Owner());
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean b) {
        authorized = b;
    }

    public User getUser() {
        return user;
    }

    public DataInputStream getDataInputStream() {
        return ois;
    }

    public void clearUser() {
        user = new User();
        authorized = false;
    }
}
