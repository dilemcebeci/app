package app;

import builder_design_pattern.MenuBuilder;
import builder_design_pattern.MenuDirector;
import command_design_pattern.CheckPagesCommand;
import command_design_pattern.UpdateFooterCommand;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class NotePad implements ChangeListener {

    private ArrayList<Page> pages = new ArrayList<Page>();
    private WelcomePage welcomePage;
    private String clipboard;
    private JFrame frame;
    private MenuDirector mDirector;
    private MenuBuilder mBuilder;
    private Page focusedPage;
    private int focusedPageIndex;
    private Footer footer;
    private JTabbedPane tabs;

    public void init() {
        mDirector = MenuDirector.getInstance();
        mBuilder = MenuBuilder.getInstance(this);


        tabs = new JTabbedPane();
        tabs.addChangeListener(this);
        welcomePage = new WelcomePage();

        mDirector.buildPagefulMenuBar(mBuilder);

        footer = new Footer(0);
        (new UpdateFooterCommand(this)).execute();

        frame = new JFrame("BBDF NotePad");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(600, 600));
        frame.setMinimumSize(new Dimension(300, 300));
        frame.setJMenuBar(mBuilder.getMenuBar());
        (new CheckPagesCommand(this)).execute();
        frame.add(footer, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void changeJTabbedPane(boolean pageful) {
        if (pageful) {
            try { frame.remove(welcomePage); } catch (Exception e) {}
            frame.add(tabs);
            frame.repaint();
            return;
        }
        try { frame.remove(tabs); } catch (Exception e) {}
        frame.add(welcomePage);
        frame.repaint();
    }

    public static Page openPage(NotePad notePad, String path) {
        String text = "";
        try {
            File file = new File(path);
            Scanner s = new Scanner(file);
            while (s.hasNext()) {
                text += s.nextLine() + "\n";
            }

            Page p = new Page(notePad, path);
            p.setText(text);
            s.close();
            return p;
        } catch (Exception e) { System.out.println("File not found."); return null; }
    }

    public void addPage() {
        Page newPage = new Page(this, null);
        pages.add(newPage);
        tabs.add(newPage);
        setFocusedPage(newPage);
    }

    public void addPage(Page page) {
        pages.add(page);
        tabs.add(page);
        setFocusedPage(page);
    }

    public void removeCurrentPage() {
        pages.remove(focusedPage);
        tabs.remove(focusedPage);
    }

    public void changeCurrentPageName(String name) {
        tabs.setTitleAt(tabs.getSelectedIndex(), name);
    }

    public Page getPageAtIndex(int index) { return pages.get(index); }

    public void setMenuBar(boolean pageful) {
        if (pageful) {
            mDirector.buildPagefulMenuBar(mBuilder);
            frame.setJMenuBar(mBuilder.getMenuBar());
            return;
        }
        mDirector.buildPagelessMenuBar(mBuilder);
        frame.setJMenuBar(mBuilder.getMenuBar());
    }

    public String getClipboard() {
        return clipboard;
    }

    public void setClipboard(String clipboard) { this.clipboard = clipboard; }

    public int getFocusedPageIndex() { return focusedPageIndex; }

    public Page getFocusedPage() {
        return focusedPage;
    }

    public void setFocusedPage(Page page) {
        focusedPage = page;
        if (page == null) { focusedPageIndex = -1; return; }
        tabs.setSelectedComponent(page);
        focusedPageIndex = tabs.getSelectedIndex();
    }

    public Footer getFooter() {
        return footer;
    }

    public boolean isEmpty() { return pages.isEmpty(); }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == tabs) {
            setFocusedPage((Page)tabs.getSelectedComponent());
        }
    }
}
