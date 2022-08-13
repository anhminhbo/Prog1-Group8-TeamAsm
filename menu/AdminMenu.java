package menu;

import User.AdminService;
import utils.Option;

public class AdminMenu extends Menu {
    private final AdminService adminService = new AdminService();

    public AdminMenu() {
        this.addOption(
                new Option("1", "Add new product to the store", adminService::addNewProduct));
        this.addOption(new Option("2", "Update product price", adminService::updatePrice));
        this.addOption(new Option("3", "View all products", adminService::viewAllProducts));
        this.addOption(new Option("4", "View all members", adminService::viewAllMembers));
        this.addOption(
                new Option(
                        "5",
                        "Log out",
                        () -> {
                            MainMenu mainMenu = new MainMenu();

                            mainMenu.run();
                        }));
        this.addOption(
                new Option(
                        "6",
                        "Exit the program",
                        () -> {
                            System.out.println("Good bye see you again.");
                            System.exit(0);
                        }));
    }
}
