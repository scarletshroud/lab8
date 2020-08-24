package src.client.gui;

import src.client.*;
import src.commands.Command_Login;
import src.commands.Command_Register;
import src.database.User;
import src.logic.ServerPacket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class AuthorizationWindow extends JFrame {
    private JPanel main;
    private JTextField loginField;
    private JButton logInButton;
    private JButton registerButton;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel loginLabel;

    private Client client;
    private User user;

    public AuthorizationWindow(Client client, Localizer localizer) {

        this.client = client;
        this.user = client.getUser();

        setContentPane(main);
        setVisible(true);
        setResizable(false);
        setSize(270, 120);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        localizeInterface(localizer.getBundle());

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 String login = loginField.getText();
                 String pass = String.valueOf(passwordField.getPassword());
                 Command_Register com = new Command_Register();
                 user.setLogin(login);
                 user.setPassword(pass);
                 client.sendRequest(com.executeOnClient(client.getAuthorized(), user, null));
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
                client.sendRequest(com.executeOnClient(client.getAuthorized(), user, null));
            }
        });
    }

    private void localizeInterface(ResourceBundle bundle) {
        logInButton.setText((String) bundle.getObject("loginButton"));
        registerButton.setText((String) bundle.getObject("registerButton"));
        passwordLabel.setText((String) bundle.getObject("passwordLabel"));
        loginLabel.setText((String) bundle.getObject("loginLabel"));
    }
}
