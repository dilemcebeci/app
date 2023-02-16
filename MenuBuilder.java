package builder_design_pattern;

import app.NotePad;
import command_design_pattern.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBuilder {

    private static MenuBuilder instance;
    private NotePad notePad;
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menu;

    private MenuBuilder(NotePad notePad) {
        this.notePad = notePad;
    }

    public void reset() {
        menuBar = new JMenuBar();
    }

    public void addFileMenu(boolean pageful) {
        menu = new JMenu("File");

        JMenuItem open = new JMenuItem("Open");
        JMenuItem newFile = new JMenuItem("New");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { (new OpenCommand(notePad)).execute(); }
        });
        newFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { (new NewFileCommand(notePad, null)).execute(); }
        });
        menu.add(open);
        menu.add(newFile);

        if (pageful) {
            JMenuItem close = new JMenuItem("Close");
            JMenuItem save = new JMenuItem("Save");
            JMenuItem clone = new JMenuItem("Clone");

            close.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { (new CloseCommand(notePad)).execute(); }
            });
            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { (new SaveCommand(notePad)).execute(); }
            });
            clone.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { (new NewFileCommand(notePad, notePad.getFocusedPage()))
                        .execute(); }
            });

            menu.add(close);
            menu.add(save);
            menu.add(clone);

        }
        menuBar.add(menu);
    }

    public void addEditMenu() {
        menu = new JMenu("Edit");

        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem paste = new JMenuItem("Paste");
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new CopyCommand(notePad)).execute();
            }
        });
        cut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { (new CutCommand(notePad)).execute(); }
        });
        paste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new PasteCommand(notePad)).execute();
            }
        });
        menu.add(copy);
        menu.add(cut);
        menu.add(paste);
        menuBar.add(menu);
    }

    public void addHelpMenu() {
        menu = new JMenu("Help");

        JMenuItem developers = new JMenuItem("Developers");
        developers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                (new DevelopersCommand(notePad)).execute();
            }
        });
        menu.add(developers);
        menuBar.add(menu);
    }

    public static MenuBuilder getInstance(NotePad notePad) {
        if (instance == null) {
            instance = new MenuBuilder(notePad);
        }
        return instance;
    }

    public JMenuBar getMenuBar() {
        JMenuBar temp = menuBar;
        reset();
        return temp;
    }

}
