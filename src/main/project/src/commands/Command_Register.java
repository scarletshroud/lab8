package src.commands;

import src.database.User;
import src.logic.Packet;
import src.logic.ServerPacket;
import src.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Command_Register extends Command implements Serializable {

    public Command_Register(){}

    @Override
    public boolean validateArgs(String ... args) {
        return args.length == 0;
    }

    public ServerPacket executeOnServer(Server server, User user, Object object)
    {
        if (server.registerUser(user.getLogin(), user.getPassword()) != -1) {
            return new ServerPacket(null, "Registration is successful!", true, true);
        } else {
            return new ServerPacket(null, "The login is already exist", false, true);
        }
    }

    @Override
    public Packet executeOnClient(boolean authorized, User user, Object args) {
        Packet packet = new Packet();
        user.setPassword(inputToHash(user.getPassword()));
        packet.wrap(this, user, null);
        return packet;
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
