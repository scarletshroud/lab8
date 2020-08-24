package src.client;

import src.client.gui.AuthorizationWindow;
import src.client.gui.Localizer;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {
        Client client = new Client("localhost", 29666);
        client.run();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            System.err.println("Look and feel not set.");
        }

        Localizer localizer = new Localizer();

        AuthorizationWindow authorizationWindow = new AuthorizationWindow(client, localizer);

        AnswerListener answerListener = new AnswerListener(client, localizer, authorizationWindow);
        answerListener.start();
    }
}
