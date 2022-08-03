package menu;

import User.MemberService;
import constant.Role;
import error.InvalidUserFormat;
import error.WrongInputType;
import product.ProductService;
import utils.Option;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainMenu extends Menu {
    private ArrayList<ProductService> ProductList = new ArrayList<>();
    private ArrayList<MemberService> MemberList = new ArrayList<>();

    public MainMenu() throws Exception {
//        Initialize ProductList, OrderList and UserList
        readProductList();
        readUserList();

//        Add options to MainMenu
        this.addOption(new Option("1", "Register to become a member\s", () ->
                register()));
        this.addOption(new Option("2", "Login with your member account\s", () ->
                login()));
        this.addOption(new Option("3", "View all the products and its details\s", () ->
                viewAllProducts()));
        this.addOption(new Option("4", "View all products sorted by price\s", () ->
                viewAllProductsByPrice()));
        this.addOption(new Option("5", "Search products by particular category", () ->
                searchProductBasedOnCategories()));
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
            MemberService newUser = new MemberService(this.MemberList.size() + 1, userName, password, fullName, phoneNumber, role, memberShip);
            BufferedWriter userWriter = new BufferedWriter(new FileWriter("repo/User.csv", true));
            if (checkIfUserExist(newUser)) {
                throw new InvalidUserFormat("The user already exited! Try again");
            }
            MemberList.add(newUser);
            userWriter.newLine();
            userWriter.write(newUser.toString());
            userWriter.close();
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
                    if (member.getRole() == Role.MEMBER) {
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
            this.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAllProducts() {

    }

    public void viewAllProductsByPrice() {

    }

    public void searchProductBasedOnCategories() {

    }

    //    Need to put this in RepoService and Initialize in the main menu
    //initialize and read data from svc files
    public void readProductList() throws Exception {
        String productItem = "";
        BufferedReader productReader = new BufferedReader(new FileReader("repo/Products.csv"));
        while (productItem != null) {
            if ((productItem = productReader.readLine()) == null) {
                continue;
            }
            String[] singleItem = productItem.split(",");
            ProductService newProduct = new ProductService(Integer.parseInt(singleItem[0]), singleItem[1], singleItem[2], singleItem[3], Float.parseFloat(singleItem[4]));
            ProductList.add(newProduct);
        }
    }

    public void readUserList() throws Exception {
        String userItem = "";
        BufferedReader userReader = new BufferedReader(new FileReader("repo/User.csv"));
        while (userItem != null) {
            if ((userItem = userReader.readLine()) == null) {
                continue;
            }
            String[] singleItem = userItem.split(",");
            MemberService newUser = new MemberService(Integer.parseInt(singleItem[0]), singleItem[1], singleItem[2], singleItem[3], Integer.parseInt(singleItem[4]), singleItem[5], singleItem[6]);
            MemberList.add(newUser);
        }
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
