package src.client.gui;

import src.client.Client;
import src.commands.*;
import src.elements.Product;
import src.logic.ServerPacket;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Workspace extends JFrame {

    private Client client;

    private JTabbedPane tabbedPane;
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
    private JLabel usernameLabel;
    private JButton executeScriptButton;
    private JButton changeLanguageButton;
    private JPanel collectionTab;

    private JPopupMenu popupMenu;
    private JMenuItem sortItem;
    private JMenuItem reverseSortItem;
    private JMenuItem filterItem;
    private JMenuItem deleteItem;
    private JMenuItem undoItem;

    private VisualisationPanel visualisationPanel;
    private CollectionTableModel collectionTableModel;
    private JTable collectionTable;
    private Object[] columnsHeader;


    private Localizer localizer;

    public Workspace(Client client, Localizer localizer) {
        this.client = client;
        this.localizer = localizer;

        setContentPane(workspace);
        setVisible(true);
        setSize(1024, 720);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        collectionTableModel = new CollectionTableModel(client, localizer);
        columnsHeader = new String[] {
                "id",
                (String) localizer.getBundle().getObject("name"),
                (String) localizer.getBundle().getObject("coordinateX"),
                (String) localizer.getBundle().getObject("coordinateY"),
                (String) localizer.getBundle().getObject("creationDate"),
                (String) localizer.getBundle().getObject("price"),
                (String) localizer.getBundle().getObject("partNumber"),
                (String) localizer.getBundle().getObject("unitOfMeasure"),
                (String) localizer.getBundle().getObject("personName"),
                (String) localizer.getBundle().getObject("personHeight"),
                (String) localizer.getBundle().getObject("personEyeColor"),
                (String) localizer.getBundle().getObject("locationName"),
                (String) localizer.getBundle().getObject("locationX"),
                (String) localizer.getBundle().getObject("locationY"),
                (String) localizer.getBundle().getObject("locationZ"),
                (String) localizer.getBundle().getObject("creator"),
        };

        collectionTableModel.setColumnIdentifiers(columnsHeader);
        collectionTable = new JTable(collectionTableModel);
        collectionTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane collectionTableScroll = new JScrollPane(collectionTable);
        collectionTable.setFillsViewportHeight(true);

        tablePanel.add(collectionTableScroll, BorderLayout.CENTER);

        nameLabel.setText(client.getUser().getLogin());

        Command_Show command_show = new Command_Show();
        client.sendRequest(command_show.executeOnClient(client.getAuthorized(), client.getUser(), null));

        visualisationPanel = new VisualisationPanel(collectionTableModel);
        tabbedPane.add("Visualisation", visualisationPanel);

        popupMenu = new JPopupMenu();
        sortItem = new JMenuItem("");
        reverseSortItem = new JMenuItem("");
        filterItem = new JMenuItem("");
        undoItem = new JMenuItem("");
        deleteItem = new JMenuItem("");

        popupMenu.add(sortItem);
        popupMenu.add(reverseSortItem);
        popupMenu.add(filterItem);
        popupMenu.add(undoItem);
        popupMenu.add(deleteItem);

        localizeInterface(localizer.getBundle());

        collectionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popupMenu.show(collectionTable, e.getX(), e.getY());
                }
            }
        });

        sortItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectionTableModel.sortByColumn(collectionTable.getSelectedColumn(), 0);
                repaint();
            }
        });

        reverseSortItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectionTableModel.sortByColumn(collectionTable.getSelectedColumn(), 1);
                repaint();
            }
        });

        filterItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectionTableModel.filterBySelectedCell(collectionTable.getSelectedColumn(), collectionTable.getSelectedRow());
                repaint();
            }
        });

        undoItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectionTableModel.unblock();
                client.sendRequest(new Command_Show().executeOnClient(client.getAuthorized(), client.getUser(), null));
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectionTableModel.deleteRow(collectionTable.getSelectedRow());
                repaint();
            }
        });


        visualisationPanel.addMouseMotionListener(new MouseAdapter() {
            private Point oldPoint;

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    oldPoint = e.getPoint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    double offsetX = oldPoint.x - e.getX();
                    double offsetY = oldPoint.y - e.getY();
                    oldPoint = e.getPoint();
                    visualisationPanel.changeCountdown(offsetX, offsetY);
                    visualisationPanel.repaint();
                }
            }
        });

        visualisationPanel.addMouseListener(new MouseAdapter() {

            private Point oldPoint;

            @Override
            public void mouseClicked(MouseEvent e) {

                    if (e.getButton() == MouseEvent.BUTTON1) {
                        visualisationPanel.drawInfo(e.getPoint());
                        oldPoint = e.getPoint();
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                      EditWindow editWindow = new EditWindow(client, localizer, collectionTableModel, visualisationPanel.getRowIndexByShape(e.getPoint()));
                    }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("old X = " + oldPoint.x + "old Y = " + oldPoint.y);
                double offsetX = oldPoint.x - e.getX();
                double offsetY = oldPoint.y - e.getY();
                oldPoint = e.getPoint();
                visualisationPanel.changeCountdown(offsetX, offsetY);
                visualisationPanel.repaint();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               AddWindow addWindow = new AddWindow(client, localizer);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.clearUser();
                new AuthorizationWindow(client, localizer);
                dispose();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveWindow removeWindow = new RemoveWindow(client, localizer);
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_Info command_info = new Command_Info();
                client.sendRequest(command_info.executeOnClient(client.getAuthorized(), client.getUser(), null));
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_Clear command_clear = new Command_Clear();
                client.sendRequest(command_clear.executeOnClient(client.getAuthorized(), client.getUser(), null));
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Command_History command_history = new Command_History();
                client.sendRequest(command_history.executeOnClient(client.getAuthorized(), client.getUser(), null));
            }
        });

        changeLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              LanguageWindow languageWindow = new LanguageWindow(localizer, Workspace.this);
            }
         });

        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String help = (String) localizer.getBundle().getObject("helpText");

                JOptionPane.showMessageDialog(Workspace.this, help);

            }
        });

        executeScriptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileChooseWindow fileChooseWindow = new FileChooseWindow(client, localizer);
            }
        });
    }

    public void localizeInterface(ResourceBundle bundle) {
        logOutButton.setText((String) bundle.getObject("logoutButton"));
        addButton.setText((String) bundle.getObject("addButton"));
        infoButton.setText((String) bundle.getObject("infoButton"));
        removeButton.setText((String) bundle.getObject("removeButton"));
       // printUniqueButton.setText((String) bundle.getObject("printUniqueButton"));
     //   sortByOwnerButton.setText((String) bundle.getObject("sortByOwnerButton"));
        historyButton.setText((String) bundle.getObject("historyButton"));
        executeScriptButton.setText((String) bundle.getObject("executeScriptButton"));
        clearButton.setText((String) bundle.getObject("clearButton"));
        helpButton.setText((String) bundle.getObject("helpButton"));
        usernameLabel.setText((String) bundle.getObject("usernameLabel"));
        clearButton.setText((String) bundle.getObject("clearButton"));
        changeLanguageButton.setText((String) bundle.getObject("languageButton"));

        tabbedPane.setTitleAt(0, (String) bundle.getObject("collectionTab"));
        tabbedPane.setTitleAt(1, (String) bundle.getObject("visualizationTab"));

        sortItem.setText((String) bundle.getObject("sortItem"));
        reverseSortItem.setText((String) bundle.getObject("reverseSortItem"));
        undoItem.setText((String) bundle.getObject("undoItem"));
        filterItem.setText((String) bundle.getObject("filterItem"));
        deleteItem.setText((String) bundle.getObject("deleteItem"));

        columnsHeader = new String[] {
                "id",
                (String) localizer.getBundle().getObject("name"),
                (String) localizer.getBundle().getObject("coordinateX"),
                (String) localizer.getBundle().getObject("coordinateY"),
                (String) localizer.getBundle().getObject("creationDate"),
                (String) localizer.getBundle().getObject("price"),
                (String) localizer.getBundle().getObject("partNumber"),
                (String) localizer.getBundle().getObject("unitOfMeasure"),
                (String) localizer.getBundle().getObject("personName"),
                (String) localizer.getBundle().getObject("personHeight"),
                (String) localizer.getBundle().getObject("personEyeColor"),
                (String) localizer.getBundle().getObject("locationName"),
                (String) localizer.getBundle().getObject("locationX"),
                (String) localizer.getBundle().getObject("locationY"),
                (String) localizer.getBundle().getObject("locationZ"),
                (String) localizer.getBundle().getObject("creator"),
        };

        collectionTableModel.setColumnIdentifiers(columnsHeader);
        repaint();

}

    public CollectionTableModel getCollectionTableModel () {
        return collectionTableModel;
    }

    public boolean isVisualizationTabOpened() {
        return tabbedPane.getSelectedIndex() == 1;
    }

    public VisualisationPanel getVisualisationPanel() {
        return visualisationPanel;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

}
