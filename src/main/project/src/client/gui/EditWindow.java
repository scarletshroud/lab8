package src.client.gui;

import src.client.Client;
import src.commands.Command_Update_By_Id;
import src.elements.Coordinates;
import src.elements.Location;
import src.elements.Person;
import src.elements.Product;

import javax.swing.*;
import javax.xml.bind.ValidationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditWindow extends JFrame {

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
    private JComboBox measureBox;
    private JComboBox colorBox;
    private JButton cancelButton;
    private JButton applyButton;
    private JPanel main;
    private JLabel nameLabel;
    private JLabel coordinateXLabel;
    private JLabel coordinateYLabel;
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

    private Client client;
    private int selectedRow;
    private CollectionTableModel collectionTableModel;
    private Product product;
    private int id;

    public EditWindow(Client client, Localizer localizer, CollectionTableModel collectionTableModel, int selectedRow) {

        this.client = client;
        this.collectionTableModel = collectionTableModel;
        this.selectedRow = selectedRow;

        setContentPane(main);
        setVisible(true);
        setResizable(false);
        setSize(310, 470);

        localizeInterface(localizer.getBundle());

        fillData();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 dispose();
            }
        });

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Product product = new Product(nameField.getText(),
                            new Coordinates(
                                    Float.parseFloat(coordinateXField.getText()),
                                    Double.parseDouble(coordinateYField.getText())),
                            LocalDate.now(), Long.parseLong(priceField.getText()),
                            partNumberField.getText(),
                            (String) measureBox.getSelectedItem(),
                            new Person(
                                    personNameField.getText(),
                                    Integer.parseInt(personHeightField.getText()),
                                    (String) colorBox.getSelectedItem(),
                                    new Location(
                                            Long.parseLong(locationXField.getText()),
                                            Long.parseLong(locationYField.getText()),
                                            Integer.parseInt(locationZField.getText()),
                                            locationNameField.getText())));
                    product.setHost(client.getUser().getLogin());
                    product.setId(id);
                    client.sendRequest(new Command_Update_By_Id().executeOnClient(client.getAuthorized(), client.getUser(), product));
                } catch (ValidationException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(EditWindow.this, "Ошибка. Несоответствие типов.");
                }
            }
        });
    }

    private void fillData() {
        product = collectionTableModel.getRow(selectedRow);
        id = product.getId();
        nameField.setText(product.getName());
        coordinateXField.setText(String.valueOf(product.getCoordinates().getX()));
        coordinateYField.setText(String.valueOf(product.getCoordinates().getY()));
        priceField.setText(String.valueOf(product.getPrice()));
        partNumberField.setText(product.getPartNumber());
        measureBox.setSelectedItem(product.getUnitOfMeasure());
        personNameField.setText(product.getOwner().getName());
        personHeightField.setText(String.valueOf(product.getOwner().getHeight()));
        colorBox.setSelectedItem(product.getOwner().getEyeColor());
        locationNameField.setText(product.getOwner().getLocation().getName());
        locationXField.setText(String.valueOf(product.getOwner().getLocation().getX()));
        locationYField.setText(String.valueOf(product.getOwner().getLocation().getY()));
        locationZField.setText(String.valueOf(product.getOwner().getLocation().getZ()));
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

        applyButton.setText((String) bundle.getObject("applyButton"));
        cancelButton.setText((String) bundle.getObject("cancelButton"));

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
