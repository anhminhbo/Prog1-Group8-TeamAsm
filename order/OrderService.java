package order;


import repo.RepoService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    //    private String[] productList;
    private static RepoService repo;
    private int orderID;
    private final int cusID;
    private boolean paidStatus;
    //    private String[] productList;
    private StringBuilder productList = new StringBuilder();

    public OrderService(int cusID) {
        this.cusID = cusID;
    }

    public OrderService(int orderID, int cusID, boolean paidStatus, String productList) {
        String[] item = productList.split(" ");
        for (int i = 0; i < item.length -1; i++) {
            if(i%2 == 0){
                this.productList.append(item[i]).append("x").append(item[i + 1]);
            }
            else{
                this.productList.append("; ");
            }
        }
        this.orderID = orderID;
        this.cusID = cusID;
        this.paidStatus = false;
//        this.productList = productList.split(" ");


    }

    public String[] getProduct() {
        return new String[] {
            Integer.toString(this.orderID),
            Integer.toString(this.cusID),
            Boolean.toString(paidStatus),
            String.valueOf(this.productList)
        };
    }

    public void createOrder() {
        try {
            Scanner scanner = new Scanner(System.in);
            List<Integer> productID = new ArrayList<>();
            List<Integer> productQuantity = new ArrayList<>();
            String input;
            while (true) {
                System.out.println("Type in the ID of the product: ");
                int pID = scanner.nextInt();
                // add check if the product exist and still available
                System.out.println("How many you want to buy: ");
                int pQuantity = scanner.nextInt();
                productID.add(pID);
                productQuantity.add(pQuantity);
                System.out.println("Do you want to buy anything else (Y/N):");
                Scanner myObj = new Scanner(System.in);
                input = myObj.nextLine().trim();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.println("Please type in Y or N:");
                    input = myObj.nextLine().trim();
                }
                if (input.equalsIgnoreCase("N")) {
                    break;
                } else assert true;
            }
//            System.out.println("You entered: " + productID);
//            System.out.println("You entered: " + productQuantity);

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < productID.size(); i++) {
                newString.append(productID.get(i).toString()).append(" ");
                newString.append(productQuantity.get(i).toString()).append(" ");
            }
//            System.out.println(newString.substring(0, newString.length() - 1));
            FileWriter pw = new FileWriter("repo/Order.csv");
            Path path = Paths.get("repo/Order.csv");
            long lines = Files.lines(path).count();
            pw.append((char) (lines + 1));

            pw.append(", ");
            pw.append((char) this.cusID);
            pw.append(", ");
            pw.append("false");
            pw.append(", ");
            pw.append(newString.substring(0, newString.length() - 1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOrderByOrderID() {
        try {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Type in the ID of the order: ");
                String orderID = scanner.nextLine().trim();
                List<String> listOfStrings = new ArrayList<>();
                BufferedReader bf = new BufferedReader(new FileReader("repo/Order.csv"));
                String line = bf.readLine();
                while (line != null) {
                    listOfStrings.add(line);
                    line = bf.readLine();
                }
                bf.close();
                String[] array = listOfStrings.toArray(new String[0]);
                for (String str : array) {
                    String[] parts = str.split(",");
                    if (orderID.equals(parts[0])){
                        System.out.println("Here is your order:");
                        System.out.println(str);
                        break;
                    }
                }
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOrderByCustomerID() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type in the ID of the order: ");
            String orderID = scanner.nextLine().trim();
            List<String> listOfStrings = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader("repo/Order.csv"));
            String line = bf.readLine();
            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
            bf.close();
            String[] array = listOfStrings.toArray(new String[0]);
            for (String str : array) {
                String[] parts = str.split(",");
                if (orderID.equals(parts[0])){
                    System.out.println("Here is the order:");
                    System.out.println(str);

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void changePaidStatus() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Type in the ID of the order: ");
            String orderID = scanner.nextLine().trim();
            List<String> listOfStrings = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader("repo/Order.csv"));
            String line = bf.readLine();
            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
            bf.close();
            String[] array = listOfStrings.toArray(new String[0]);
            for (String str : array) {
                String[] parts = str.split(",");
                if (orderID.equals(parts[0])){
                    System.out.println("Here is the order:");
                    System.out.println(str);
                    System.out.println("What do you want to change the paid status into? (true/false)");
                    String newStatus = scanner.nextLine().trim().toLowerCase();
                    while (!newStatus.equals("true") && !newStatus.equals("false")) {
                        System.out.println("Wrong paid status.");
                        System.out.println("Type again:");
                        newStatus = scanner.nextLine().trim().toLowerCase();
                    }
                    System.out.println(parts[2]);
                    parts[2] = newStatus.replace(parts[2], newStatus);
                    System.out.println(parts[2]);
                    //need help with writing into csv file
                    System.out.println(str);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
