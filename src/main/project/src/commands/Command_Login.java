package src.commands;

import src.database.User;
import src.logic.CollectionManager;
import src.logic.Packet;
import src.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Command_Login extends Command implements Serializable {

    public Command_Login(){}

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    @Override
    public String executeOnServer(Server server, User user, Object object) {
        if (server.checkUser(user.getLogin(), user.getPassword())) {
            return "Authorization is successful!";
        } else {
            return "The user with this login and password does not exist";
        }
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, String ... args) {
        Packet packet = new Packet();
        try {
            String login = null;
            String pass = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the login.");
            while (login == null) {
                if (br.ready()) {
                    login = br.readLine();
                }
            }
            System.out.println("Enter the password");
            while (pass == null) {
                if (br.ready()) {
                    pass = inputToHash(br.readLine());
                }
            }

            user.setLogin(login);
            user.setPassword(pass);

            packet.wrap(this, user, null);
            return packet;
        } catch (IOException ex) {
            System.out.println("Problems with buffered reader.");
        }
        return null;
    }

    private String inputToHash(String pass) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            byte[] hashInBytes = messageDigest.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Algorithm not found.");
        }
        return null;
    }
}
