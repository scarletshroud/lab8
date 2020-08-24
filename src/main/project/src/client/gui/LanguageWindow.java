package src.client.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class LanguageWindow extends JFrame {
    private JPanel main;
    private JRadioButton russianRadioButton;
    private JRadioButton norwegianRadioButton;
    private JRadioButton panamanianRadioButton;
    private JButton cancelButton;
    private JButton applyButton;
    private JRadioButton catalanRadioButton;

    private Localizer localizer;
    private Workspace workspace;

    public LanguageWindow(Localizer localizer, Workspace workspace) {

        this.localizer = localizer;
        this.workspace = workspace;

        setContentPane(main);
        setVisible(true);
        setResizable(false);
        setSize(200, 250);
        setDefaultLookAndFeelDecorated(true);

        localizeInterface(localizer.getBundle());

        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (russianRadioButton.isSelected()) {
                    localizer.setRus();
                }
                if (norwegianRadioButton.isSelected()) {
                    localizer.setNor();
                }
                if (catalanRadioButton.isSelected()) {
                    localizer.setCat();
                }
                if (panamanianRadioButton.isSelected()) {
                    localizer.setPan();
                }
                localizeInterface(localizer.getBundle());
                workspace.localizeInterface(localizer.getBundle());
                workspace.repaint();
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void localizeInterface(ResourceBundle bundle) {
        russianRadioButton.setText((String) bundle.getObject("russianRadioButton"));
        norwegianRadioButton.setText((String) bundle.getObject("norwegianRadioButton"));
        panamanianRadioButton.setText((String) bundle.getObject("panamanianRadioButton"));
        cancelButton.setText((String) bundle.getObject("cancelButton"));
        applyButton.setText((String) bundle.getObject("applyButton"));
        catalanRadioButton.setText((String) bundle.getObject("catalanRadioButton"));
    }
}





