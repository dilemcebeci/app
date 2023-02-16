package builder_design_pattern;

public class MenuDirector {

    private static MenuDirector instance;

    private MenuDirector() {}

    public static MenuDirector getInstance() {
        if (instance == null) {
            instance = new MenuDirector();
        }
        return instance;
    }

    public void buildPagelessMenuBar(MenuBuilder builder) {
        builder.addFileMenu(false);
        builder.addHelpMenu();
    }

    public void buildPagefulMenuBar(MenuBuilder builder) {
        builder.addFileMenu(true);
        builder.addEditMenu();
        builder.addHelpMenu();
    }

}
