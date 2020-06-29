package src.client.gui;

import src.client.Client;
import src.commands.Command;
import src.commands.Command_Add;
import src.commands.Command_Add_If_Max;
import src.commands.Command_Add_If_Min;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;
import src.logic.ServerPacket;

import javax.swing.*;
import javax.xml.bind.ValidationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class AddWindow extends JFrame {

    Client client;

    private JPanel main;
    private JTextField nameField;
    private JTextField locationZField;
    private JTextField locationYField;
    private JTextField coordinateXField;
    private JTextField locationNameField;
    private JTextField locationXField;
    private JTextField coordinateYField;
    private JTextField personHeightField;
    private JTextField personNameField;
    private JTextField priceField;
    private JTextField partNumberField;
    private JRadioButton addRadioButton;
    private JRadioButton addIfMaxRadioButton;
    private JRadioButton addIfMinRadioButton;
    private JButton applyButton;
    private JComboBox measureBox;
    private JComboBox colorBox;

    public AddWindow(Client client) {
        this.client = client;

        measureBox.addItem("PCS");
        measureBox.addItem("MILLILITERS");
        measureBox.addItem("GRAMS");

        colorBox.addItem("RED");
        colorBox.addItem("BLUE");
        colorBox.addItem("ORANGE");
        colorBox.addItem("WHITE");
        colorBox.addItem("BROWN");

        setContentPane(main);
        setVisible(true);
        setResizable(false);
        setSize(310, 470);

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command command = new Command_Add();

                if (addIfMaxRadioButton.isSelected()) {
                    command = new Command_Add_If_Max();
                } else {
                    if (addIfMinRadioButton.isSelected()) {
                        command = new Command_Add_If_Min();
                    }
                }
                String[] args = buildElement();
                client.sendRequest(command.executeOnClient(client.getAuthorized(), client.getUser(), args));
                ServerPacket serverPacket = client.acceptAnswer();
                dispose();
            }
        });
    }

    private String[] buildElement() {
        String[] row = {
                nameField.getText(),
                coordinateXField.getText(),
                coordinateYField.getText(),
                priceField.getText(),
                partNumberField.getText(),
                (String) measureBox.getSelectedItem(),
                personNameField.getText(),
                personHeightField.getText(),
                (String) colorBox.getSelectedItem(),
                locationNameField.getText(),
                locationXField.getText(),
                locationYField.getText(),
                locationZField.getText(),
                client.getUser().getLogin()

        };
        return row;
    }
}
