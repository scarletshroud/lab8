package src.client.gui;

import src.client.Client;
import src.commands.Command_Remove_By_Id;
import src.logic.ServerPacket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class RemoveWindow extends JFrame {
    private JPanel panel1;
    private JTextField idField;
    private JButton applyButton;
    private JLabel idLabel;
    private Client client;

    RemoveWindow(Client client, Localizer localizer) {

        this.client = client;

        setContentPane(panel1);
        setVisible(true);
        setResizable(false);
        setSize(250, 100);

        localizeInterface(localizer.getBundle());

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                Command_Remove_By_Id  command_remove_by_id = new Command_Remove_By_Id();
                client.sendRequest(command_remove_by_id.executeOnClient(client.getAuthorized(), client.getUser(), id));
            }
        });
    }

    public void localizeInterface(ResourceBundle bundle) {
        applyButton.setText((String) bundle.getObject("applyButton"));
    }
}
