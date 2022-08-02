package Menu;

import Product.*;
import Error.*;
import User.MemberService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private ArrayList<ProductService> ProductList = new ArrayList<>();
    private ArrayList<MemberService> MemberList = new ArrayList<>();
    private MemberService CurrentUser = null;

    public MainMenu() throws Exception {
        readProductList();
        readUserList();
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

    public MemberService getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(MemberService currentUser) {
        CurrentUser = currentUser;
    }


    public void run() {
        welcome();
        while (this.getCurrentUser() == null) {
            unregisteredMenu();
        }
        while(this.getCurrentUser() != null){
            registeredMenu();
        }
    }

    public void unregisteredMenu() {
        Scanner snc = new Scanner(System.in);
        try {
            System.out.println("""
                    1.Register to become a member\s
                    2.Login with your member account\s
                    3.View all the products and its details\s
                    4.View all products sorted by price\s
                    5.Search products by particular category
                    """);
            System.out.println("What do you want to do ?");
            try {
                int choice = snc.nextInt();
                snc.nextLine();
                if (choice > 5 || choice < 1) {
                    throw new InvalidExceptionOption();
                }
                switch (choice) {
                    case 1 -> register();
                    case 2 -> login();
                    case 3 -> viewAllProducts();
                    case 4 -> viewAllProductsByPrice();
                    case 5 -> searchProductBasedOnCategories();
                }
            } catch (InvalidExceptionOption e) {
                throw new InvalidExceptionOption("We do not offer this option! Try the available options", e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registeredMenu(){

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
            BufferedWriter userWriter = new BufferedWriter(new FileWriter("Repo/User.csv", true));
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
                    this.CurrentUser = member;
                }
            }
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

    //initialize and read data from svc files
    public void readProductList() throws Exception {
        String productItem = "";
        BufferedReader productReader = new BufferedReader(new FileReader("Repo/Products.csv"));
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
        BufferedReader userReader = new BufferedReader(new FileReader("Repo/User.csv"));
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

    private void printDelimiter() {
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private void welcome(){
        printDelimiter();
        System.out.println("""
                COSC2081 GROUP ASSIGNMENT\s
                STORE ORDER MANAGEMENT SYSTEM\s
                Instructor: Mr. Minh Vu\s
                Group: Hyper mega super ultra extra giga OOP java team\s
                s3891909, Truong Bach Minh\s
                s3924577, Nguyen Nguyen Khuong\s
                s3927201, Tran Ngoc Khang\s
                s3931605, Nguyen Cuong Anh Minh""");
        printDelimiter();
    }
}
