package menu;

import utils.Option;

public class AdminMenu extends Menu {
    public AdminMenu() {
        this.addOption(new Option("1", "Option 1 AdminMenu", () -> {
            System.out.println("Callback function AdminMenu here");
        }));
        this.addOption(new Option("5", "Log out", () ->
        {
            MainMenu mainMenu = new MainMenu();

            mainMenu.run();
        }));
        this.addOption(new Option("6", "Exit the program", () ->
        {
            System.out.println("Good bye see you again.");
            System.exit(0);
        }));
    }
}
