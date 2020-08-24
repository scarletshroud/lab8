package src.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessageWindow extends JDialog
{
    public MessageWindow (JFrame owner, String answer) {
        super(owner, "Сообщение От Сервера", false);

        String htmlAnswer = "<html> " + answer;
        htmlAnswer = htmlAnswer.replace("\n", "<br>") + " </html>";
        JLabel label = new JLabel(htmlAnswer);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);


        JButton ok = new JButton("Ок");
        ok.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                setVisible(false);
            }
        });

        JPanel panel = new JPanel();
        panel.add(ok);
        add(panel, BorderLayout.SOUTH);
        setSize(300, 300);
    }
}