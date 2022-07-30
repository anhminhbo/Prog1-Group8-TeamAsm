package Menu;
import Product.*;
import User.MemberService;

//import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private ArrayList<ProductService> ProductList = new ArrayList<ProductService>();
    private ArrayList<MemberService> MemberList = new ArrayList<MemberService>();

    public MainMenu() throws Exception{
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

    public void readUserList() throws Exception{
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

    public void register(){
        try {
            Scanner snc = new Scanner(System.in);
            System.out.println("Enter your Username:");
            String userName = snc.nextLine();
            System.out.println("Enter your Password:");
            String password = snc.nextLine();
            System.out.println("Enter your Full Name:");
            String fullName = snc.nextLine();
            System.out.println("Enter your Phone Number:");
            int phoneNumber = snc.nextInt();
            snc.nextLine();
            System.out.println("Enter your current role:");
            String role = snc.nextLine();
            System.out.println("Enter your rank (if a member):");
            String memberShip = snc.nextLine();
            MemberService newUser = new MemberService(this.MemberList.size() + 1,userName, password, fullName, phoneNumber, role, memberShip);
            String lastLine = "";
            BufferedWriter userWriter = new BufferedWriter(new FileWriter("Repo/User.csv", true));
            userWriter.newLine();
            userWriter.write(newUser.toString());
            userWriter.close();
        }
        catch (Exception e){
            System.out.println(e + "Something went wrong but i dont know xD!");
        }



    }
    public void login(){

    }
}
