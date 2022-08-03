package menu;

import utils.Option;

public class MemberMenu extends Menu {
    public MemberMenu() {
        this.addOption(new Option("1", "Option 1 MemberMenu", () -> {
            System.out.println("Callback function for MemberMenu here");
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
