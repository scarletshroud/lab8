package src.client;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.exceptions.InvalidCommand;
import src.logic.CommandHandler;
import src.commands.*;
import src.logic.Packet;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Client implements Runnable {

    private CommandHandler commandHandler;
    private DataInputStream ois;
    private DataOutputStream oos;

    private final String host;
    private final int port;
    private final int BUFFER_SIZE = 4096;

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        ois = null;
        oos = null;

        commandHandler = new CommandHandler();
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 258);
        client.run();
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket("localhost", 258);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            oos = new DataOutputStream(socket.getOutputStream());
            ois = new DataInputStream(socket.getInputStream());

            logger.info("Client connected to socket");
            logger.info("Client writing channel = oos & reading channel = ois initialized.");

            System.out.println("Hello, you are connected to server.");

            boolean close = false;
            registerCommands();

            while(!socket.isOutputShutdown()) {

                if(br.ready()){
                    logger.info("Client started writing a message to server...");
                    String userInput = br.readLine();
                    close = handleRequest(userInput);

                    if (close) {
                        break;
                    }
                }
            }

            oos.close();
            ois.close();
            socket.close();
            logger.info("Closing the connection...");
            System.out.println("Goodbye!");

        } catch (IOException | InterruptedException  ex) {
            ex.printStackTrace();
        }
    }

    private boolean handleRequest(String userInput) throws IOException, InterruptedException{

        try {
            Boolean close = false;

            Command command = commandHandler.pickCommand(userInput);

            if (command != null) {
                String[] args = createArgs(userInput);

                if (userInput.contains("execute_script")) {
                    Command_Execute_Script com = (Command_Execute_Script) command;
                    ArrayList<Packet> packets = com.execute(userInput);
                    if (packets != null) {
                        for (Packet p : packets) {
                            sendRequest(p, userInput);
                        }
                    }
                } else {
                    Packet packet = command.executeOnClient(args);
                    return sendRequest(packet, userInput);
                }
            }
        } catch (InvalidCommand ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    private boolean sendRequest(Packet packet, String userInput) throws IOException {
        try {
            if (packet != null) {

                byte[] message = serializeObject(packet);
                oos.write(message);
                oos.flush();
                logger.info("Client sent message " + userInput + " to server.");

                if (userInput.contains("exit")) {
                    logger.info("Client stopped working with server.");
                    return true;
                }

                logger.info("Client sent message & start waiting for data from server...");
                System.out.println("Sending data...");
                System.out.print("Waiting for the answer from server");

                while (ois.available() <= 0) {
                }

                logger.info("Trying to read data...");
                byte[] bytes = new byte[BUFFER_SIZE];
                ois.read(bytes);
                String input = new String(bytes);
                System.out.println(input);
            }
        } catch (SocketException ex) {
            logger.info("Client lost connection with server. Stopping work.");
            System.out.println("At the moment server is not working");
            return true;
        }
        return false;
    }

    private byte[] serializeObject(Packet packet) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(packet);
        objectOutputStream.flush();

        return byteArrayOutputStream.toByteArray();
    }

    private String[] createArgs(String input) {
        String[] args = input.split(" ");
        String[] argsToSend = new String[args.length-1];
        System.arraycopy(args, 1, argsToSend, 0, args.length-1);
        return argsToSend;
    }

    private void registerCommands() {
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
}
