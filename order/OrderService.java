package order;

import User.MemberService;
import product.ProductService;
import repo.RepoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    private static final String[] labelFields = {"Order ID", "Customer ID", "Paid Status"};
    private static RepoService repo = new RepoService();
    private int orderID;
    private int cusID;
    private boolean paidStatus;
    private String productList = "";

    public OrderService(RepoService repo) {
        if (OrderService.repo == null) OrderService.repo = repo;
    }

    public OrderService(int orderID, int cusID, boolean paidStatus, String productList) {
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
    }

    public static String[] getLabelFields() {
        return labelFields;
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
                System.out.println("Enter the ID of the product: ");
                int pID = scanner.nextInt();
                // check if the product exist and still available
                System.out.println("Enter the desired quantity: ");
                int pQuantity = scanner.nextInt();
                productID.add(pID);
                productQuantity.add(pQuantity);
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
            System.out.println("You entered: " + productID);
            System.out.println("You entered: " + productQuantity);

            StringBuilder newString = new StringBuilder();
            for (int i = 0; i < productID.size(); i++) {
                newString.append(productID.get(i).toString()).append(" ");
                newString.append(productQuantity.get(i).toString()).append(" ");
            }
            System.out.println(newString.substring(0, newString.length() - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public float getTotalPrice(){
        ArrayList<ProductService> ProductList = repo.readProductList();
        String[] products = this.productList.split(";");
        float res = 0;
        for (String product: products){
            String[] temp = product.split("x");
            System.out.println(temp[0] + " " + temp[1]);
            for (ProductService Product: ProductList){
                if (Integer.parseInt(temp[0]) == Product.getProductID()){
                    res += Product.getPrice() * Integer.parseInt(temp[1]);
                    break;
                }
            }
        }
        return res;
    }

    public void setPaidStatus(boolean paidStatus) {
        if (this.paidStatus) return;
        this.paidStatus = paidStatus;
        ArrayList<MemberService> MemberList = repo.readUserList();
        for (MemberService Member: MemberList){
            if (this.cusID == Member.getMemberID()){
                Member.updateAccumulatedMoney(getTotalPrice());
                Member.updateMemberShip();
                break;
            }
        }
        repo.writeIntoUserFile(MemberList, false);
    }

    public int getOrderID() {
        return orderID;
    }

    public int getCusID() {
        return cusID;
    }

    public boolean getPaidStatus() {
        return paidStatus;
    }

    public String getProductList() {
        return productList;
    }

    public String convertProductList(){
        String res = productList;
        res = res.replace('x', ' ');
        res = res.replace(';', ' ');
        return res;
    }

    public String toDataLine(){
        return this.orderID
                + ","
                + this.cusID
                + ","
                + this.paidStatus
                + ","
                + convertProductList()
                + "\n";
    }

    public static void main(String[] args) {
        ArrayList<OrderService> OrderList = repo.readOrderList();
        for (OrderService order: OrderList){
            order.setPaidStatus(true);
        }
        repo.writeIntoOrderFile(OrderList, false);
    }
}
