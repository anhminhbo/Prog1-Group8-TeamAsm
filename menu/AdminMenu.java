package menu;

import User.AdminService;
import User.MemberService;
import product.ProductService;
import utils.Option;

public class AdminMenu extends Menu {

    public AdminMenu() {
        AdminService adminService = new AdminService();
        ProductService productService = new ProductService(super.getRepo());
        MemberService memberService = new MemberService(super.getRepo());

        this.addOption(
                new Option("1", "Add new product to the store", productService::addNewProduct));
        this.addOption(new Option("2", "Update product price", productService::updatePrice));
        this.addOption(new Option("3", "View all products", productService::viewAllProducts));
        this.addOption(new Option("4", "View all members", memberService::viewAllMembers));
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
