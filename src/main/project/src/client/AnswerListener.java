package src.client;

import src.client.Client;
import src.client.gui.*;
import src.elements.Product;
import src.logic.ServerPacket;

import javax.swing.*;
import java.util.ArrayList;
import java.util.MissingResourceException;

public class AnswerListener extends Thread {

    private CollectionTableModel collectionTableModel;
    private Client client;
    private Localizer localizer;
    private Workspace workspace;
    private AuthorizationWindow authorizationWindow;
    private final int BUFFER_SIZE = 4096;

    AnswerListener(Client client, Localizer localizer, AuthorizationWindow authorizationWindow) {
        this.authorizationWindow = authorizationWindow;
        this.client = client;
        this.localizer = localizer;
    }

    @Override
    public void run() {

        while (true) {
                ServerPacket serverPacket = client.acceptAnswer();
                if (serverPacket != null) {

                    if (!client.getAuthorized() && serverPacket.getIsMessage() && serverPacket.getIsSuccessful()) {
                        if (serverPacket.getMessage().equals("Authorization is successful!")) {
                            client.setAuthorized(true);
                            authorizationWindow.dispose();
                            workspace = new Workspace(client, localizer);
                            collectionTableModel = workspace.getCollectionTableModel();
                        }

                        try {
                            JOptionPane.showMessageDialog(null, localizer.getBundle().getObject(serverPacket.getMessage()));
                        } catch (MissingResourceException ex) {
                            JOptionPane.showMessageDialog(null, serverPacket.getMessage());
                        }

                    } else {

                        if (client.getAuthorized() & !serverPacket.getIsMessage()) {
                            if (!collectionTableModel.isBlocked()) {
                                collectionTableModel.updateAll((ArrayList<Product>) serverPacket.getObject());
                            }
                            workspace.repaint();
                            workspace.getVisualisationPanel().updateShapesToDraw(workspace.isVisualizationTabOpened());
                        }

                        if (client.getAuthorized() & serverPacket.getIsMessage()) {
                            try {
                                JDialog dialog = new MessageWindow(null, (String) localizer.getBundle().getObject(serverPacket.getMessage()));
                                dialog.setVisible(true);
                            } catch (MissingResourceException ex) {
                                JDialog dialog = new MessageWindow(null, serverPacket.getMessage());
                                dialog.setVisible(true);
                            }
                        }
                    }
                }
        }
    }
}
