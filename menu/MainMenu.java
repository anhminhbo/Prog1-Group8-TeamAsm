package menu;

import error.InvalidUserFormat;
import error.WrongInputType;
import product.ProductService;
import user.MemberService;
import utils.Option;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainMenu extends Menu {
    private final MemberService memberService = new MemberService(super.getRepo());
    private final ProductService productService = new ProductService(super.getRepo(), super.getScanner());
    public MainMenu() {
//        Add options to MainMenu
        this.addOption(new Option("1", "Register to become a member\s", this::register));
        this.addOption(new Option("2", "Login with your account\s", this::login));
        this.addOption(new Option("3", "View all the products and its details\s", productService::viewAllProducts));
        this.addOption(new Option("4", "View all products sorted by price\s", productService::viewAllProductsByPrice));
        this.addOption(new Option("5", "Search products by particular category", productService::searchProductBasedOnCategories));
        this.addOption(new Option("6", "Exit the program", () ->
        {
            System.out.println("Good bye see you again.");
            System.exit(0);
        }));
    }

    //main method for MainService
    public void register() {
        try {
            Scanner snc = super.getScanner();
            System.out.println("Enter your Username:");
            String userName = snc.nextLine().trim();
            System.out.println("Enter your Password:");
            String password = snc.nextLine().trim();
            System.out.println("Enter your Full Name:");
            String fullName = snc.nextLine().trim();
            System.out.println("Enter your Phone Number:");
            String phoneNumber = snc.nextLine().trim();
            try{
                Integer.parseInt(phoneNumber);
            }
            catch (Exception e){
                throw new WrongInputType("Invalid phone number !Try again");
            }
            MemberService newUser = new MemberService(String.valueOf(memberService.getMemberList().size() + 1),
                    userName, password, fullName,
                    phoneNumber, 0);
            BufferedWriter userWriter = new BufferedWriter(new FileWriter("repo/User.csv",
                    true));
            if (checkIfUserExist(newUser)) {
                throw new InvalidUserFormat("The user already exited! Try again");
            }
            memberService.getMemberList().add(newUser);
            userWriter.newLine();
            userWriter.write(newUser.toDataLine());
            userWriter.close();
            System.out.println("Registered successfully! Login to enjoy the system");
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
  

    }

    public void login() {
        Scanner snc = super.getScanner();
        try {
            System.out.println("Enter your Username:");
            String userName = snc.nextLine().trim();
            System.out.println("Enter your Password:");
            String password = snc.nextLine().trim();
            for (MemberService member : memberService.getMemberList()) {
                if (member.getUserName().equals(userName) && member.getPassword().equals(password)) {
                    if (member.getUserName().equals("admin")) {
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.run();
                    } else {
                        MemberMenu memberMenu = new MemberMenu(member.getMemberID());
                        memberMenu.run();
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
    
    //sub method that helps to optimize the project
    public boolean checkIfUserExist(MemberService user) {
        for (MemberService member : memberService.getMemberList()) {
            if (member.getUserName().equalsIgnoreCase(user.getUserName())) {
                return true;
            }
        }
        return false;
    }
}
