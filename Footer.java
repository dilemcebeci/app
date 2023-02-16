package app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Footer extends JPanel {
    private JLabel charCount;

    public Footer(int count) {
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());
        charCount = new JLabel(String.valueOf(count));
        charCount.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 10));
        charCount.setFont(new Font(null, Font.PLAIN, 10) );
        this.add(charCount, BorderLayout.WEST);
    }

    public String getCharCountText() {
        return charCount.getText();
    }

    public void setCharCountText(String string) {
        charCount.setText(string);
    }
}
