package menu;

import utils.Option;
import order.*;

public class MemberMenu extends Menu {
    public MemberMenu() {
        OrderService orderService = new OrderService(CurrentUser.getMemberID());
        this.addOption(new Option("1", "Create order", orderService::createOrder));

        this.addOption(new Option("2", "Search order", orderService::getOrderByOrderID));
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
