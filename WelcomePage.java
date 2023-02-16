package app;

import javax.swing.*;
import java.awt.*;

public class WelcomePage extends JTabbedPane {
    private JPanel panel;
    private JLabel welcomeMessage;

    public WelcomePage() {
        panel = new JPanel();
        welcomeMessage = new JLabel("Welcome to BBDF NotePad! Open a file to start editing.", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font(null, Font.BOLD, 18));
        panel.setLayout(new BorderLayout());
        panel.add(welcomeMessage, BorderLayout.CENTER);
        this.add("Welcome!", panel);
    }

}
