package src.client.gui;

import src.client.Client;
import src.commands.*;
import src.elements.Product;
import src.logic.ServerPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Workspace extends JFrame {

    private Client client;

    private JTabbedPane tabbedPane1;
    private JButton logOutButton;
    private JButton addButton;
    private JButton infoButton;
    private JButton removeButton;
    private JButton printUniqueButton;
    private JButton sortByOwnerButton;
    private JButton historyButton;
    private JButton clearButton;
    private JPanel workspace;
    private JLabel nameLabel;
    private JPanel buttonPanel;
    private JPanel tablePanel;
    private JButton helpButton;
    private TableManager tableManager;
    private CollectionTableModel collectionTableModel;

    public Workspace(Client client) {
        this.client = client;

        setContentPane(workspace);
        setVisible(true);
        setSize(1024, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        collectionTableModel = new CollectionTableModel();
        JTable collectionTable = new JTable(collectionTableModel);
        JScrollPane collectionTableScroll = new JScrollPane(collectionTable);
        collectionTable.setFillsViewportHeight(true);

        tablePanel.add(collectionTableScroll, BorderLayout.CENTER);

        nameLabel.setText(client.getUser().getLogin());

        Command_Show command_show = new Command_Show();
        client.sendRequest(command_show.executeOnClient(client.getAuthorized(), client.getUser(), null));
        ServerPacket serverPacket = client.acceptAnswer();

        TableManager tableManager = new TableManager(client.getDataInputStream(), collectionTableModel);
        tableManager.start();

        if (serverPacket.getIsSuccessful()) {
            ArrayList<Product> products = (ArrayList<Product>) serverPacket.getObject();
            collectionTableModel.updateAll(products);
        } else {
            JOptionPane.showMessageDialog(Workspace.this, serverPacket.getMessage());
        }

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               AddWindow addWindow = new AddWindow(client);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.clearUser();
                new AuthorizationWindow(client);
                dispose();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveWindow removeWindow = new RemoveWindow(client);
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_Info command_info = new Command_Info();
                client.sendRequest(command_info.executeOnClient(client.getAuthorized(), client.getUser(), null));
                ServerPacket serverPacket = client.acceptAnswer();
                JOptionPane.showMessageDialog(Workspace.this, serverPacket.getMessage());
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_Clear command_clear = new Command_Clear();
                client.sendRequest(command_clear.executeOnClient(client.getAuthorized(), client.getUser(), null));
                ServerPacket serverPacket = client.acceptAnswer();
                JOptionPane.showMessageDialog(Workspace.this, serverPacket.getMessage());
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_History command_history = new Command_History();
                client.sendRequest(command_history.executeOnClient(client.getAuthorized(), client.getUser(), null));
                ServerPacket serverPacket = client.acceptAnswer();
                JOptionPane.showMessageDialog(Workspace.this, serverPacket.getMessage());
            }
        });

        printUniqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_Print_Field_Descending_Owner  command_print_field_descending_owner = new Command_Print_Field_Descending_Owner();
                client.sendRequest(command_print_field_descending_owner.executeOnClient(client.getAuthorized(), client.getUser(), null));
                ServerPacket serverPacket = client.acceptAnswer();
                JOptionPane.showMessageDialog(Workspace.this, serverPacket.getMessage());
            }
        });

        sortByOwnerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_Filter_By_Unit_Of_Measure command_filter_by_unit_of_measure = new Command_Filter_By_Unit_Of_Measure();
                client.sendRequest(command_filter_by_unit_of_measure.executeOnClient(client.getAuthorized(),client.getUser(), null));
                ServerPacket serverPacket = client.acceptAnswer();
                JOptionPane.showMessageDialog(Workspace.this, serverPacket.getMessage());
            }
        });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String help = "\ninfo : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)" +
                        "\nshow : вывести в стандартный поток вывода все элементы коллекции в строковом представлении" +
                        "\nadd {element} : добавить новый элемент в коллекцию" +
                        "\nupdate id {element} : обновить значение элемента коллекции, id которого равен заданному" +
                        "\nremove_by_id id : удалить элемент из коллекции по его id" +
                        "\nclear : очистить коллекцию" +
                        "\nsave : сохранить коллекцию в файл" +
                        "\nexecute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме" +
                        "\nexit : завершить программу (без сохранения в файл)" +
                        "\nadd_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции" +
                        "\nadd_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции" +
                        "\nhistory : вывести последние 11 команд (без их аргументов)" +
                        "\nfilter_by_unit_of_measure unitOfMeasure : вывести элементы, значение поля unitOfMeasure которых равно заданному" +
                        "\nprint_unique_part_number partNumber : вывести уникальные значения поля partNumber" +
                        "\nprint_field_descending_owner owner : вывести значения поля owner в порядке убывания\n";

                JOptionPane.showMessageDialog(Workspace.this, help);

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
