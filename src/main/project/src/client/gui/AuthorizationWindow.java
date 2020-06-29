package src.client.gui;

import src.client.*;
import src.commands.Command_Login;
import src.commands.Command_Register;
import src.database.User;
import src.logic.ServerPacket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorizationWindow extends JFrame {
    private JPanel main;
    private JTextField loginField;
    private JButton logInButton;
    private JButton registerButton;
    private JPasswordField passwordField;

    private Client client;
    private User user;

    public AuthorizationWindow(Client client) {

        this.client = client;
        this.user = client.getUser();

        setContentPane(main);
        setVisible(true);
        setResizable(false);
        setSize(250, 120);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String login = loginField.getText();
                 String pass = String.valueOf(passwordField.getPassword());
                 Command_Register com = new Command_Register();
                 user.setLogin(login);
                 user.setPassword(pass);
                 client.sendRequest(com.executeOnClient(client.getAuthorized(), user));
                 String answer = client.acceptAnswer().getMessage();
                 JOptionPane.showMessageDialog(AuthorizationWindow.this, answer);
            }
        });

        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String pass = String.valueOf(passwordField.getPassword());
                Command_Login com = new Command_Login();
                user.setLogin(login);
                user.setPassword(pass);
                client.sendRequest(com.executeOnClient(client.getAuthorized(), user));

                ServerPacket serverPacket = client.acceptAnswer();

                if (!client.getAuthorized() && serverPacket.getIsSuccessful()) {
                    client.setAuthorized(true);
                    Workspace workspace = new Workspace(client);
                    dispose();
                }

                JOptionPane.showMessageDialog(AuthorizationWindow.this, serverPacket.getMessage());
            }
        });
    }
}
