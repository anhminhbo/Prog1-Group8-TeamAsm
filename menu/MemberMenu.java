package menu;

import order.OrderService;
import utils.Option;

public class MemberMenu extends Menu {
    private int memberID;

    public MemberMenu(String memberID) {
        OrderService orderService = new OrderService(memberID, super.getRepo(), super.getScanner());
        this.addOption(new Option("1", "Create order", orderService::createOrder));
        this.addOption(new Option("2", "Search order", orderService::getOrderByOrderID));
        this.addOption(new Option("3", "View your orders", orderService::getAllOrderOfMember));
        this.addOption(
                new Option(
                        "4",
                        "Log out",
                        () -> {
                            MainMenu mainMenu = new MainMenu();

                            mainMenu.run();
                        }));
        this.addOption(
                new Option(
                        "5",
                        "Exit the program",
                        () -> {
                            System.out.println("Good bye see you again.");
                            System.exit(0);
                        }));
    }

    public int getMemberID() {
        return memberID;
    }
}
