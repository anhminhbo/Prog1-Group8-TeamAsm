package order;

import User.MemberService;
import product.ProductService;
import repo.RepoService;
import tableFormatter.TableFormatterService;
import utils.Convert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("StringConcatenationInLoop")
public class OrderService {
    private static final String[] labelFields = {"Order ID", "Customer ID", "Paid Status", "Product - Quantity", "Total Price"};
    private static RepoService repo = new RepoService();
    private String orderID;
    private final String cusID;
    private boolean paidStatus;
    private String productList = "";
    private double totalPrice;

    public OrderService(String memberID, RepoService repo) {
        this.cusID = memberID;
        if (OrderService.repo == null){
            OrderService.repo = repo;
        }
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCusID() {
        return cusID;
    }

    public boolean getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(boolean paidStatus) {
        this.paidStatus = paidStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String convertProductList(){
        String res = productList;
        res = res.replace('x', ' ');
        res = res.replace(';', ' ');
        return res;
    }

    public OrderService(String orderID, String cusID, boolean paidStatus, String productList, double totalPrice) {
        String[] item = productList.split(" ");
        for (int i = 0; i < item.length - 1; i++) {
            if (i % 2 == 0) {
                this.productList += item[i] + "x" + item[i + 1];
            } else {
                this.productList += ";";
            }
        }
        this.orderID = orderID;
        this.cusID = cusID;
        this.paidStatus = paidStatus;
        this.totalPrice = totalPrice;
    }

    public static String[] getLabelFields() {
        return labelFields;
    }

    public String toDataLine(){
        return this.orderID
                + ","
                + this.cusID
                + ","
                + this.paidStatus
                + ","
                + convertProductList()
                + ","
                + Convert.toDecimal(this.totalPrice)
                + "\n";
    }
//working
    public String[] getOrderRow(){
        ArrayList<ProductService> ProductList = repo.readProductList();
        String[] products = this.productList.split(";");
        String listOfProduct = "";
        for (String product: products) {
            String[] temp = product.split("x");
            for (ProductService Product: ProductList){
                if (temp[0].equals(Product.getProductID())){
                    listOfProduct += Product.getProductName() + " - " + temp[1] + "; ";
                    break;
                }
            }
        }
        return new String[]{
                String.valueOf(this.orderID),
                String.valueOf(this.cusID),
                (this.paidStatus ? "Paid" : "Unpaid"),
                listOfProduct,
                Convert.toDecimal(this.totalPrice)
        };
    }

    public void createOrder() {
        try {
            Scanner scanner = new Scanner(System.in);
            ArrayList<String> productID = new ArrayList<>();
            ArrayList <Integer> productQuantity = new ArrayList<>();
            ArrayList <ProductService> ProductList = repo.readProductList();
            ArrayList <MemberService> MemberList = repo.readUserList();
            TableFormatterService tableFormatter = new TableFormatterService(ProductService.getLabelFields());
            for (ProductService product: ProductList){
                tableFormatter.addRows(product.toProductRow());
            }
            tableFormatter.display();

            String input;
            double totalPrice = 0;
            while (true) {
                System.out.print("Enter product ID: ");
                String pID = String.valueOf(scanner.nextInt());
                // check if the product exist and still available
                System.out.print("Enter the desired quantity: ");
                int pQuantity = scanner.nextInt();
                productID.add(pID);
                productQuantity.add(pQuantity);
                for (ProductService product: ProductList){
                    if (product.getProductID().equals(pID)){
                        totalPrice += product.getPrice() * pQuantity;
                        break;
                    }
                }
                System.out.println("Do you want to buy anything else (Y/N): ");
                Scanner myObj = new Scanner(System.in);
                input = myObj.nextLine().trim();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.println("Please type in Y or N: ");
                    input = myObj.nextLine().trim();
                }
                if (input.equalsIgnoreCase("N")) {
                    break;
                } else assert true;
            }
            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < productID.size(); i++) {
                newString.append(productID.get(i)).append(" ");
                newString.append(productQuantity.get(i).toString()).append(" ");
            }

            // Get Discount
            for (MemberService Member: MemberList){
                if (cusID.equals(Member.getMemberID())){
                    System.out.println(Member.getMemberShip());
                    switch (Member.getMemberShip()) {
                        case "Platinum" -> totalPrice -= totalPrice * 0.15;
                        case "Gold" -> totalPrice -= totalPrice * 0.1;
                        case "Silver" -> totalPrice -= totalPrice * 0.05;
                    }
                    System.out.println(totalPrice);
                    break;
                }
            }

            ArrayList<OrderService> newData = new ArrayList<>();
            newData.add(new OrderService(String.valueOf(repo.readOrderList().size() + 1), this.cusID, false, newString.toString(), totalPrice));
            repo.writeIntoOrderFile(newData, true);
            System.out.println(newString.substring(0, newString.length() - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//working
    public void getOrderByOrderID() {
        System.out.print("Enter order ID: ");
        Scanner scanner = new Scanner(System.in);
        String orderID = scanner.nextLine().trim();
        TableFormatterService tableFormatter =
                new TableFormatterService(OrderService.getLabelFields());
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order: OrderList){
            if (!order.getOrderID().equals(orderID)) continue;
            tableFormatter.addRows(order.getOrderRow());
        }
        tableFormatter.display();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void getOrderByCustomerID() {
        System.out.print("Enter customer ID: ");
        Scanner scanner = new Scanner(System.in);
        String cusID = scanner.nextLine().trim();
        TableFormatterService tableFormatter =
                new TableFormatterService(OrderService.getLabelFields());
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order: OrderList){
            if (!order.getCusID().equals(cusID)) continue;
            tableFormatter.addRows(order.getOrderRow());
        }
        if(tableFormatter.getRows().size() == 0){
            System.out.println("The customer doesn't exist or they dont have any orders");
            System.out.println("Returning back...");
            return;
        }
        else{
            tableFormatter.display();
        }
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void changePaidStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter order ID: ");
        String orderID = String.valueOf(scanner.nextInt()).trim();
        ArrayList<OrderService> OrderList = repo.readOrderList();
        ArrayList<MemberService> MemberList = repo.readUserList();
        TableFormatterService tableFormatter = new TableFormatterService(OrderService.getLabelFields());
        boolean paidStatus = false;
        String memberID = "";
        double totalPrice = 0;

        for (OrderService Order: OrderList){
            if (orderID.equals(Order.getOrderID())){
                memberID = Order.getCusID();
                totalPrice = Order.getTotalPrice();
                tableFormatter.addRows(Order.getOrderRow());
                System.out.println("Here is the order:");
                tableFormatter.display();
                System.out.print("Enter paid status (Paid(P)/ Unpaid(U): ");
                String newStatus = scanner.next().trim().toLowerCase();
                while (!newStatus.equals("p") && !newStatus.equals("u")) {
                    System.out.println("Wrong paid status.");
                    System.out.println("Enter again:");
                    newStatus = scanner.nextLine().trim().toLowerCase();
                }
                paidStatus = (newStatus.equals("p"));
                if (Order.getPaidStatus() == paidStatus) return;
                Order.setPaidStatus(paidStatus);
                break;
            }
        }
        if(tableFormatter.getRows().size() == 0){
            System.out.println("The order doesn't exist. Try again !");
            System.out.println("Returning back");
            return;
        }
        repo.writeIntoOrderFile(OrderList, false);

        for (MemberService Member: MemberList){
            if (Member.getMemberID().equals(memberID)){
                Member.updateAccumulatedMoney(totalPrice, paidStatus);
                Member.updateMemberShip();
                break;
            }
        }
        repo.writeIntoUserFile(MemberList, false);
    }
}
