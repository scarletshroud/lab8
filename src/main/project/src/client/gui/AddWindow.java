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
import java.util.ResourceBundle;

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
    private JPanel enterLabel;
    private JLabel coordinateXLabel;
    private JLabel priceLabel;
    private JLabel partNumberLabel;
    private JLabel unitOfMeasureLabel;
    private JLabel personNameLabel;
    private JLabel personHeightLabel;
    private JLabel personEyeColorLabel;
    private JLabel locationNameLabel;
    private JLabel locationXLabel;
    private JLabel locationYLabel;
    private JLabel locationZLabel;
    private JLabel nameLabel;
    private JLabel coordinateYLabel;

    public AddWindow(Client client, Localizer localizer) {
        this.client = client;

        setContentPane(main);
        setVisible(true);
        //setResizable(false);
        setSize(610, 470);

        localizeInterface(localizer.getBundle());

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
                Product product = buildElement();
                if (product != null) {
                    client.sendRequest(command.executeOnClient(client.getAuthorized(), client.getUser(), product));
                    dispose();
                }
            }
        });
    }

    private Product buildElement() {
        try {

            String unitOfMeasure = new String();
            switch (measureBox.getSelectedIndex()) {
                case 0:
                    unitOfMeasure = "PCS";
                    break;
                case 1:
                    unitOfMeasure = "MILLILITERS";
                    break;
                case 2:
                    unitOfMeasure = "GRAMS";
                    break;
            }

            String color = new String();
            switch (colorBox.getSelectedIndex()) {
                case 0:
                    color = "RED";
                    break;
                case 1:
                    color = "BLUE";
                    break;
                case 2:
                    color = "ORANGE";
                    break;
                case 3:
                    color = "WHITE";
                    break;
                case 4:
                    color = "BROWN";
                    break;
            }


            Product product = new Product(nameField.getText(),
                    new Coordinates(
                            Float.parseFloat(coordinateXField.getText()),
                            Double.parseDouble(coordinateYField.getText())),
                    LocalDate.now(), Long.parseLong(priceField.getText()),
                    partNumberField.getText(),
                    unitOfMeasure,
                    new Person(
                            personNameField.getText(),
                            Integer.parseInt(personHeightField.getText()),
                            color,
                            new Location(
                                    Long.parseLong(locationXField.getText()),
                                    Long.parseLong(locationYField.getText()),
                                    Integer.parseInt(locationZField.getText()),
                                    locationNameField.getText())));
            product.setHost(client.getUser().getLogin());
            return product;

        } catch (ValidationException | NumberFormatException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private void localizeInterface(ResourceBundle bundle) {

        coordinateXLabel.setText((String) bundle.getObject("coordinateX"));
        coordinateYLabel.setText((String) bundle.getObject("coordinateY"));
        priceLabel.setText((String) bundle.getObject("price"));
        partNumberLabel.setText((String) bundle.getObject("partNumber"));
        unitOfMeasureLabel.setText((String) bundle.getObject("unitOfMeasure"));
        personNameLabel.setText((String) bundle.getObject("personName"));
        personHeightLabel.setText((String) bundle.getObject("personHeight"));
        personEyeColorLabel.setText((String) bundle.getObject("personEyeColor"));
        locationNameLabel.setText((String) bundle.getObject("locationName"));
        locationXLabel.setText((String) bundle.getObject("locationX"));
        locationYLabel.setText((String) bundle.getObject("locationY"));
        locationZLabel.setText((String) bundle.getObject("locationZ"));
        nameLabel.setText((String) bundle.getObject("name"));

        addRadioButton.setText((String) bundle.getObject("addRadioButton"));
        addIfMaxRadioButton.setText((String) bundle.getObject("addIfMaxRadioButton"));
        addIfMinRadioButton.setText((String) bundle.getObject("addIfMinRadioButton"));

        applyButton.setText((String) bundle.getObject("applyButton"));

        measureBox.addItem((String) bundle.getObject("PCS"));
        measureBox.addItem((String) bundle.getObject("MILLILITERS"));
        measureBox.addItem((String) bundle.getObject("GRAMS"));

        colorBox.addItem((String) bundle.getObject("RED"));
        colorBox.addItem((String) bundle.getObject("BLUE"));
        colorBox.addItem((String) bundle.getObject("ORANGE"));
        colorBox.addItem((String) bundle.getObject("WHITE"));
        colorBox.addItem((String) bundle.getObject("BROWN"));

    }
}
