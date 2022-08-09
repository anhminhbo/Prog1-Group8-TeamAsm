package Order;


import User.MemberService;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Array;
import Error.*;

public class OrderService {
    private final int orderID;
    private final int cusID;
    private boolean paidStatus;
//    private String[] productList;
    private StringBuilder productList = new StringBuilder();
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

    public String[] getProduct(){
        return new String[]{
                Integer.toString(this.orderID), Integer.toString(this.cusID), Boolean.toString(paidStatus), String.valueOf(this.productList)
        };
    }

    public void createOrder(){
        try {
            Scanner scanner = new Scanner(System.in);
            List<Integer> productID = new ArrayList<>();
            List<Integer> productQuantity = new ArrayList<>();
            String input;
            while (true) {
                System.out.println("Type in the ID of the product: ");
                int pID = scanner.nextInt();
                //check if the product exist and still available
                System.out.println("How many you want to buy: ");
                int pQuantity = scanner.nextInt();
                productID.add(pID);
                productQuantity.add(pQuantity);
                System.out.println("Do you want to buy anything else (Y/N):");
                Scanner myObj = new Scanner(System.in);
                input = myObj.nextLine().trim();
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"))
                {
                    System.out.println("Please type in Y or N:");
                    input = myObj.nextLine().trim();
                }
                if (input.equalsIgnoreCase("N")) {
                    break;
                } else assert true;
            }
            System.out.println("You entered: " + productID);
            System.out.println("You entered: " + productQuantity);

            StringBuilder newString = new StringBuilder();
            for(int i = 0; i < productID.size(); i++) {
                newString.append(productID.get(i).toString()).append(" ");
                newString.append(productQuantity.get(i).toString()).append(" ");
            }
            System.out.println(newString.substring(0, newString.length() - 1));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
