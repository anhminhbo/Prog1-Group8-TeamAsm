package order;


import repo.RepoService;
import tableFormatter.TableFormatterService;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
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
    public OrderService(int cusID, RepoService repo) {
        this.cusID = cusID;
        if (OrderService.repo == null) OrderService.repo = repo;
    }
    private boolean paidStatus;
    //    private String[] productList;
    private final StringBuilder productList = new StringBuilder();

    public static void pressEnterToContinue(){
        System.out.println("Press Enter key to continue...");
        try{
            System.in.read();
        } catch(Exception e){
            e.printStackTrace();
        }
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

    public static String productFormat(String[] orderElement, String orderInfo){
        String[] item = orderElement[3].split(" ");
        StringWriter orderFormat = new StringWriter();
        for (int i = 0; i < item.length - 1; i++) {
            if (i % 2 == 0) {
                orderFormat.append(item[i]).append("x").append(item[i + 1]);
            } else {
                orderFormat.append("; ");
            }
        }
        return orderInfo.replace(orderElement[3],orderFormat.toString());
    }

    public String[] getOrder() {
        return new String[] {
                Integer.toString(this.orderID),
                Integer.toString(this.cusID),
                Boolean.toString(paidStatus),
                String.valueOf(this.productList)
        };
    }

    public static String[] readOrderList() {
        return repo.readOrderList();
    }

    private static final String[] labelFields = {
            "Order ID", "User ID", "Paid Status", "Products"
    };
    public static String[] getLabelFields() {
        return labelFields;
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
            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < productID.size(); i++) {
                newString.append(productID.get(i).toString()).append(" ");
                newString.append(productQuantity.get(i).toString()).append(" ");
            }
//            System.out.println(newString.substring(0, newString.length() - 1));
            FileWriter pw = new FileWriter("repo/Order.csv");
            Path path = Paths.get("repo/Order.csv");
            try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getOrderByOrderID() {
        try {
            System.out.println("Type in the ID of the order: ");
            Scanner scanner = new Scanner(System.in);
            String orderID = scanner.nextLine().trim();
            String[] array = readOrderList();
            String[] orderArray = new String[0];
            for (String orderInfo : array) {
                String[] orderElement = orderInfo.split(",");
                if (orderID.equals(orderElement[0])) {
                    System.out.println("Here is your order:");
                    orderArray = productFormat(orderElement,orderInfo).split(",");
                    break;
                }
            }
            TableFormatterService tableFormatter = new TableFormatterService(OrderService.getLabelFields());
            tableFormatter.addRows(orderArray);
            tableFormatter.display();
            pressEnterToContinue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getOrderByCustomerID() {
        try {
            System.out.println("Type in the ID of the order: ");
            Scanner scanner = new Scanner(System.in);
            String orderID = scanner.nextLine().trim();
            String[] array = readOrderList();
            String[] orderArray = new String[0];
            for (String orderInfo : array) {
                String[] orderElement = orderInfo.split(",");
                if (orderID.equals(orderElement[0])){
                    System.out.println("Here is the order:");
                    System.out.println(orderInfo);
                    orderArray = productFormat(orderElement,orderInfo).split(",");
                }
            }
            TableFormatterService tableFormatter = new TableFormatterService(OrderService.getLabelFields());
            tableFormatter.addRows(orderArray);
            tableFormatter.display();
            pressEnterToContinue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void changePaidStatus() {
        try {
            Scanner scanner = new Scanner(System.in);
            String orderID = scanner.nextLine().trim();
            String[] array = readOrderList();
            for (String orderInfo : array) {
                String[] orderElement = orderInfo.split(",");
                if (orderID.equals(orderElement[0])){
                    System.out.println("Here is the order:");
                    System.out.println(orderInfo);
                    System.out.println("What do you want to change the paid status into? (true/false)");
                    String newStatus = scanner.nextLine().trim().toLowerCase();
                    while (!newStatus.equals("true") && !newStatus.equals("false")) {
                        System.out.println("Wrong paid status.");
                        System.out.println("Type again:");
                        newStatus = scanner.nextLine().trim().toLowerCase();
                    }
                    orderElement[2] = newStatus.replace(orderElement[2], newStatus);
                    String newString = String.join(",", orderElement);


                    String filePath = "repo/Order.csv";
                    Scanner sc = new Scanner(new File(filePath));
                    StringBuilder buffer = new StringBuilder();
                    while (sc.hasNextLine()) {
                        buffer.append(sc.nextLine()).append(System.lineSeparator());
                    }
                    String fileContents = buffer.toString();
                    System.out.println("Contents of the file: "+fileContents);
                    sc.close();
                    fileContents = fileContents.replaceAll(orderInfo, newString);
                    FileWriter writer = new FileWriter(filePath);
                    System.out.println();
                    System.out.println("new data: \n"+fileContents);
                    writer.append(fileContents);
                    writer.flush();
                }
            }
            pressEnterToContinue();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
