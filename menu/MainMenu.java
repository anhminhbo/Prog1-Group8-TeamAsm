package menu;

import User.MemberService;
import constant.Role;
import error.InvalidUserFormat;
import error.WrongInputType;
import product.ProductService;
import utils.Option;
import tableFormatter.TableFormatterService;
import repo.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainMenu extends Menu {


    public MainMenu() {
        RepoService repo = new RepoService();
//        Initialize ProductList, OrderList and UserList
        this.ProductList = repo.readProductList();
        this.MemberList = repo.readUserList();

//        Add options to MainMenu
        this.addOption(new Option("1", "Register to become a member\s", this::register));
        this.addOption(new Option("2", "Login with your member account\s", this::login));
        this.addOption(new Option("3", "View all the products and its details\s", this::viewAllProducts));
        this.addOption(new Option("4", "View all products sorted by price\s", this::viewAllProductsByPrice));
        this.addOption(new Option("5", "Search products by particular category", this::searchProductBasedOnCategories));
        this.addOption(new Option("6", "Exit the program", () ->
        {
            System.out.println("Good bye see you again.");
            System.exit(0);
        }));
    }

    public ArrayList<ProductService> getProductList() {
        return ProductList;
    }

    public void setProductList(ArrayList<ProductService> productList) {
        ProductList = productList;
    }

    public ArrayList<MemberService> getMemberList() {
        return MemberList;
    }

    public void setMemberList(ArrayList<MemberService> memberList) {
        MemberList = memberList;
    }


    //main method for MainService
    public void register() {
        try {
            Scanner snc = new Scanner(System.in);
            System.out.println("Enter your Username:");
            String userName = snc.nextLine();
            System.out.println("Enter your Password:");
            String password = snc.nextLine();
            System.out.println("Enter your Full Name:");
            String fullName = snc.nextLine();
            System.out.println("Enter your Phone Number:");
            int phoneNumber;
            try {
                phoneNumber = snc.nextInt();
                snc.nextLine();
            } catch (Exception e) {
                throw new WrongInputType("Please input the number for the phone! Not String", e);
            }
            System.out.println("Enter your current role:");
            String role = snc.nextLine();
            System.out.println("Enter your rank (if a member):");
            String memberShip = snc.nextLine();
            MemberService newUser = new MemberService(this.MemberList.size() + 1, userName, password, fullName, phoneNumber, role.toUpperCase(), memberShip);
            BufferedWriter userWriter = new BufferedWriter(new FileWriter("repo/User.csv", true));
            if (checkIfUserExist(newUser)) {
                throw new InvalidUserFormat("The user already exited! Try again");
            }
            this.MemberList.add(newUser);
            userWriter.newLine();
            userWriter.write(newUser.toString());
            userWriter.close();
            System.out.println("Registered successfully !Login to enjoy the system");
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void login() {
        Scanner snc = new Scanner(System.in);
        try {
            System.out.println("Enter your Username to login:");
            String userName = snc.nextLine().trim();
            System.out.println("Enter your Password to login:");
            String password = snc.nextLine().trim();
            for (MemberService member : MemberList) {
                if (member.getUserName().equals(userName) && member.getPassword().equals(password)) {
                    System.out.println(member.getRole());
                    if (Objects.equals(member.getRole(), Role.MEMBER)) {
                        MemberMenu memberMenu = new MemberMenu();
                        memberMenu.run();
                    } else {
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.run();
                    }
                }
            }
            System.out.println("Invalid username or password. Try again later");
            TimeUnit.SECONDS.sleep(3);
//            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllProducts() {
        try {
            TableFormatterService productTable = new TableFormatterService(
                    new String[]{"PRODUCT ID", "PRODUCT NAME", "PRODUCT CATEGORY",
                            "DESCRIPTION", "PRICE"}
            );
            for (int i = 0; i < this.getProductList().size(); i++) {
                productTable.addRows(
                        this.getProductList().get(i).getProduct()
                );
            }
            productTable.display();
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllProductsByPrice() {
        Scanner snc = new Scanner(System.in);
        try {
            TableFormatterService productTable = new TableFormatterService(
                    new String[]{"PRODUCT ID", "PRODUCT NAME", "PRODUCT CATEGORY",
                            "DESCRIPTION", "PRICE"}
            );
            ArrayList<ProductService> sortedProductsASC;
            sortedProductsASC = this.ProductList;

            for (ProductService productService : sortedProductsASC) {
                productTable.addRows(
                        productService.getProduct()
                );
            }
            productTable.display();
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchProductBasedOnCategories() {

    }

    //sub method that helps to optimize the project
    public boolean checkIfUserExist(MemberService user) {
        for (MemberService member : MemberList) {
            if (member.getUserName().equalsIgnoreCase(user.getUserName())) {
                return true;
            }
        }
        return false;
    }


}
