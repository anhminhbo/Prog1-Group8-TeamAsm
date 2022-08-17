package menu;

import User.MemberService;
import product.ProductService;
import utils.Option;

public class AdminMenu extends Menu {

    public AdminMenu() {
        ProductService productService = new ProductService(super.getRepo());
        MemberService memberService = new MemberService(super.getRepo());

        this.addOption(
                new Option("1", "Add new product to the store", productService::addNewProduct));
        this.addOption(
                new Option("2", "Remove product from the store", productService::removeProduct));
        this.addOption(new Option("3", "Update product price", productService::updatePrice));
        this.addOption(new Option("4", "View all products", productService::viewAllProducts));
        this.addOption(new Option("5", "View all members", memberService::viewAllMembers));
        this.addOption(
                new Option(
                        "6",
                        "Log out",
                        () -> {
                            MainMenu mainMenu = new MainMenu();

                            mainMenu.run();
                        }));
        this.addOption(
                new Option(
                        "7",
                        "Exit the program",
                        () -> {
                            System.out.println("Good bye see you again.");
                            System.exit(0);
                        }));
    }
}
