package src.client.gui;

import src.client.Client;
import src.commands.Command_Execute_Script;
import src.logic.Packet;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FileChooseWindow extends JFrame {
    private JPanel main;
    private JTextField pathField;
    private JButton openButton;
    private JButton applyButton;
    private JButton cancelButton;
    private JLabel pathLabel;
    private Client client;
    private Localizer localizer;


    public FileChooseWindow(Client client, Localizer localizer) {

        this.localizer = localizer;

        setContentPane(main);
        setVisible(true);
        setSize(400, 200);

        localizeInterface(localizer.getBundle());

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if (f.getName().endsWith("txt")) {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "only txt files";
                    }
                });
                fileChooser.showDialog(FileChooseWindow.this, "Select");
                File file = fileChooser.getSelectedFile();
                pathField.setText(file.getPath());
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = pathField.getText();
                Command_Execute_Script com = new Command_Execute_Script(client.getCommandHandler());
                ArrayList<Packet> packets = com.execute(client.getAuthorized(), client.getUser(), path);
                if (packets != null) {
                    for (Packet p : packets) {
                        client.sendRequest(p);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                dispose();
            }
        });
    }

    public void localizeInterface(ResourceBundle bundle) {
        openButton.setText((String) bundle.getObject("openButton"));
        pathLabel.setText((String) bundle.getObject("pathLabel"));
        cancelButton.setText((String) bundle.getObject("cancelButton"));
        applyButton.setText((String) bundle.getObject("applyButton"));
    }
}
