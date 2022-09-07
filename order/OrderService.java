package order;

import constant.Membership;
import constant.PaidStatus;
import error.InvalidUserFormat;
import error.isValidDateFormat;
import product.ProductService;
import repo.RepoService;
import tableFormatter.TableFormatterService;
import user.MemberService;
import utils.Convert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("StringConcatenationInLoop")
public class OrderService {
    private static final String[] labelFields = {"Order ID", "Customer ID", "Paid Status", "Product - Quantity", "Date", "Total Price"};
    private static RepoService repo;
    private static Scanner scanner;
    private String cusID;
    private String orderID;
    private String paidStatus;
    private String productList = "";
    private String date;
    private double totalPrice;

    public OrderService(RepoService repo, Scanner scanner) {
        if (OrderService.repo == null) {
            OrderService.repo = repo;
        }
        if (OrderService.scanner == null) OrderService.scanner = scanner;
    }

    public OrderService(String memberID, RepoService repo, Scanner scanner) {
        this.cusID = memberID;
        if (OrderService.repo == null) {
            OrderService.repo = repo;
        }
        if (OrderService.scanner == null) OrderService.scanner = scanner;

    }


    public OrderService(String orderID, String cusID, String paidStatus, String productList, String date, double totalPrice) {
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
        this.date = date;
        this.totalPrice = totalPrice;
    }

    public static String[] getLabelFields() {
        return labelFields;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCusID() {
        return cusID;
    }

    public String getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(String paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getDate() {
        return date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    private String convertProductList() {
        String res = productList;
        res = res.replace('x', ' ');
        res = res.replace(';', ' ');
        return res;
    }

    public String toDataLine() {
        return this.orderID
                + ","
                + this.cusID
                + ","
                + this.paidStatus
                + ","
                + convertProductList()
                + ","
                + this.date
                + ","
                + Convert.toDecimal(this.totalPrice)
                + "\n";
    }

    //working
    private String[] getOrderRow() {
        ArrayList<ProductService> ProductList = repo.readProductList();
        String[] products = this.productList.split(";");
        String listOfProduct = "";
        for (String product : products) {
            String[] temp = product.split("x");
            for (ProductService Product : ProductList) {
                if (temp[0].equals(Product.getProductID())) {
                    listOfProduct += Product.getProductName() + " - " + temp[1] + "; ";
                    break;
                }
            }
        }
        return new String[]{
                String.valueOf(this.orderID),
                String.valueOf(this.cusID),
                this.paidStatus,
                listOfProduct,
                this.date,
                Convert.toDecimal(this.totalPrice)
        };
    }

    @SuppressWarnings("SwitchStatementWithoutDefaultBranch")
    public void createOrder() {
        try {
            ArrayList<String> productID = new ArrayList<>();
            ArrayList<Integer> productQuantity = new ArrayList<>();
            ArrayList<ProductService> ProductList = repo.readProductList();
            ArrayList<MemberService> MemberList = repo.readUserList();
            TableFormatterService tableFormatter = new TableFormatterService(ProductService.getLabelFields());
            for (ProductService product : ProductList) {
                if (product.getProductID().equals("UNKNOWN")) continue;
                tableFormatter.addRows(product.toProductRow());
            }
            tableFormatter.display();

            String input;
            double totalPrice = 0;
            double productPrice = 0;
            String membershipStatus = "";
            while (true) {
                System.out.print("Enter product ID: ");
                String pID = String.valueOf(scanner.nextInt());
                boolean isIDExist = false;
                for (ProductService product: ProductList){
                    if (Objects.equals(product.getProductID(), pID)){
                        isIDExist = true;
                        break;
                    }
                }
                if (!isIDExist) {
                    System.out.println("Product is not exist. Please enter again.");
                    return;
                }
                // check if the product exist and still available
                System.out.print("Enter the desired quantity: ");
                int pQuantity = scanner.nextInt();
                productID.add(pID);
                productQuantity.add(pQuantity);
                for (ProductService product : ProductList) {
                    if (product.getProductID().equals(pID)) {
                        totalPrice += product.getPrice() * pQuantity;
                        break;
                    }
                }
                System.out.println("Do you want to buy anything else (Y/N): ");
                Scanner myObj = scanner;
                input = myObj.nextLine().trim();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.println("Please type in Y or N: ");
                    input = myObj.nextLine().trim();
                }
                if (input.equalsIgnoreCase("N")) {
                    break;
                } else {
    
                    StringBuilder newString = new StringBuilder();
                    for (int i = 0; i < productID.size(); i++) {
                        newString.append(productID.get(i)).append(" ");
                        newString.append(productQuantity.get(i).toString()).append(" ");
                    }
    
                    // Get Discount
                    for (MemberService Member : MemberList) {
                        if (cusID.equals(Member.getMemberID())) {
                            membershipStatus = Member.getMemberShip();
                            productPrice = totalPrice;
                            switch (membershipStatus) {
                                case Membership.Platinum -> totalPrice -= totalPrice * 0.15;
                                case Membership.Gold -> totalPrice -= totalPrice * 0.1;
                                case Membership.Silver -> totalPrice -= totalPrice * 0.05;
                            }
                            break;
                        }
                    }
    
                    ArrayList<OrderService> newData = new ArrayList<>();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String strDate = dateFormat.format(date);
                    newData.add(new OrderService(String.valueOf(repo.readOrderList().size() + 1), this.cusID, PaidStatus.Unpaid, newString.toString(), strDate, totalPrice));
                    repo.writeIntoOrderFile(newData, true);
                    System.out.println("Thank for ordering! Here is the details");
                    TableFormatterService orderConfirmedDisplay = new TableFormatterService(ProductService.getLabelFields());
                    for (ProductService product : ProductList) {
                        if (!product.getProductID().equals(String.valueOf(repo.readOrderList().size() + 1))) continue;
                        orderConfirmedDisplay.addRows(product.toProductRow());
                    }
                    orderConfirmedDisplay.display();
                    System.out.println("The total products price is: " + productPrice);
                    switch (membershipStatus) {
                        case Membership.Platinum -> System.out.println("You got 15% discount as a " + Membership.Platinum + " member");
                        case Membership.Gold -> System.out.println("You got 10% discount as a " + Membership.Gold + " member");
                        case Membership.Silver -> System.out.println("You got 5% discount as a " + Membership.Silver + " member");
                        case Membership.None -> System.out.println("You are not a VIP member, so that there is no discount");
                    }
                    System.out.println("The payment : " + totalPrice);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //working
    public void getOrderByOrderID() {
        System.out.print("Enter order ID: ");
        String orderID = scanner.nextLine().trim();
        TableFormatterService tableFormatter =
                new TableFormatterService(OrderService.getLabelFields());
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order : OrderList) {
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
        String cusID = scanner.nextLine().trim();
        TableFormatterService tableFormatter =
                new TableFormatterService(OrderService.getLabelFields());
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order : OrderList) {
            if (!order.getCusID().equals(cusID)) continue;
            tableFormatter.addRows(order.getOrderRow());
        }
        if (tableFormatter.getRows().size() == 0) {
            System.out.println("The customer doesn't exist or they don't have any orders");
            System.out.println("Returning back...");
            return;
        } else {
            tableFormatter.display();
        }
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void getAllOrders(){
        TableFormatterService displayAllCustomerOrders =
                new TableFormatterService(OrderService.getLabelFields());
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order : OrderList) {
            displayAllCustomerOrders.addRows(order.getOrderRow());
        }
        if (displayAllCustomerOrders.getRows().size() == 0) {
            System.out.println("There are no orders in the history or processing");
            System.out.println("Returning back...");
            return;
        } else {
            displayAllCustomerOrders.display();
        }
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void changePaidStatus() {
        System.out.print("Enter order ID: ");
        String orderID;
        try {
            orderID = String.valueOf(scanner.nextInt()).trim();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid order ID, please try again.");
            scanner.nextLine();
            return;
        }
        ArrayList<OrderService> OrderList = repo.readOrderList();
        ArrayList<MemberService> MemberList = repo.readUserList();
        TableFormatterService tableFormatter = new TableFormatterService(OrderService.getLabelFields());
        String paidStatus = PaidStatus.Unpaid;
        String memberID = "";
        double totalPrice = 0;

        for (OrderService Order : OrderList) {
            if (orderID.equals(Order.getOrderID())) {
                memberID = Order.getCusID();
                totalPrice = Order.getTotalPrice();
                tableFormatter.addRows(Order.getOrderRow());
                System.out.println("Here is the order:");
                tableFormatter.display();
                System.out.print("Enter paid status (Paid(P)/ Unpaid(U): ");
                String newStatus = scanner.next().trim().toLowerCase();
                while (!newStatus.equals("p") && !newStatus.equals("u")) {
                    System.out.println("Wrong paid status.");
                    System.out.println("Enter paid status (Paid(P)/ Unpaid(U) again :");
                    newStatus = scanner.nextLine().trim().toLowerCase();
                }
                paidStatus = (newStatus.equals("p")) ? PaidStatus.Paid : PaidStatus.Unpaid;
                if (Order.getPaidStatus().equals(paidStatus)) return;
                Order.setPaidStatus(paidStatus);
                System.out.println("The status of your chosen Order has been changed.");
                break;
            }
        }
        if (tableFormatter.getRows().size() == 0) {
            System.out.println("The order doesn't exist. Try again !");
            System.out.println("Returning back");
            return;
        }
        repo.writeIntoOrderFile(OrderList, false);

        boolean isAdditionToAccumulatedMoney = paidStatus.equals(PaidStatus.Paid);
        for (MemberService Member : MemberList) {
            if (Member.getMemberID().equals(memberID)) {
                Member.updateAccumulatedMoney(totalPrice, isAdditionToAccumulatedMoney);
                Member.updateMemberShip();
                break;
            }
        }
        repo.writeIntoUserFile(MemberList, false);
    }

    public void showOrderListAndCalculateTotalRevenue() {
        ArrayList<OrderService> OrderList = repo.readOrderList();

        try {
            System.out.println("Enter the day that you want to calculate revenue - dd/mm/yyyy");
            String input = scanner.nextLine();
            double totalRevenue = 0;
            isValidDateFormat validChecker = new isValidDateFormat();
            if (validChecker.isValidFormat(input)) {
                input = String.valueOf(new SimpleDateFormat("dd/MM/yyyy").parse(input));
                TableFormatterService tableFormatter =
                        new TableFormatterService(OrderService.getLabelFields());
                for (OrderService order : OrderList) {
                    if ((!String.valueOf(new SimpleDateFormat("dd/MM/yyyy").parse(order.getDate())).equals(input)) || (order.getPaidStatus().equals(PaidStatus.Unpaid)))
                        continue;
                    totalRevenue += order.getTotalPrice();
                    tableFormatter.addRows(order.getOrderRow());
                }
                if (tableFormatter.getRows().size() == 0) {
                    System.out.println("There is no order executed on that day !");
                    System.out.println("Returning back...");
                    return;
                }
                tableFormatter.display();
                System.out.println("The total revenue : " + String.format("%.2f", totalRevenue));
            }
            else{
                throw new InvalidUserFormat();
            }
        } catch (Exception e) {
            System.out.println("Something went wrong!");
            System.out.println("Returning back...");
        }


    }

    public void getAllOrderOfMember() {
        TableFormatterService displayAllOrders =
                new TableFormatterService(OrderService.getLabelFields());
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order : OrderList) {
            if (!order.getCusID().equals(this.cusID)) continue;
            displayAllOrders.addRows(order.getOrderRow());
        }
        if (displayAllOrders.getRows().size() == 0) {
            System.out.println("You do not have any orders in processing");
            System.out.println("Returning back...");
            return;
        } else {
            displayAllOrders.display();
        }
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
