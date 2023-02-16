package app;

import command_design_pattern.UpdateFooterCommand;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;

public class Page extends JPanel implements KeyListener {

    private String path;
    private String name;
    private JTextArea textArea;
    private NotePad notePad;

    public Page(NotePad notePad, String path) {
        this.path = path;
        parseName(path);
        this.notePad = notePad;
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        textArea = new JTextArea();
        textArea.addKeyListener(this);
        textArea.setLineWrap(true);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    private Page(Page page) {
        this(page.notePad, null);
        name = page.name;
        int i = name.length() - 1;
        while (i >= 0 && name.charAt(i) != '.') { i--; }
        name = name.substring(0, i) + "(Clone)" + name.substring(i);
        this.path = null;
        setText(page.getText());
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void insertTextAtCaret(String string) {
        textArea.insert(string, textArea.getCaretPosition());
    }

    public String getClipboard() { return notePad.getClipboard(); }

    public void setClipboard(String string) { notePad.setClipboard(string); }

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        this.textArea.setText(text);
    }

    public String getSelectedText() {
        return textArea.getSelectedText();
    }

    public int getSelectionStart() {
        return textArea.getSelectionStart();
    }

    public int getSelectionEnd() {
        return textArea.getSelectionEnd();
    }

    public String getName() {
        return name;
    }

    public void parseName(String path) {
        if (path == null) {
            name = "Adsiz.txt";
            return;
        }
        int i = path.length() - 1;
        while (i >= 0 && path.charAt(i) != '/') {
            i--;
        }
        name = path.substring(i + 1);
    }

    public Page clone() {
        return new Page(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == textArea) {
            (new UpdateFooterCommand(notePad)).execute();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
