package repo;

import User.MemberService;
import order.OrderService;
import product.ProductService;

import java.io.*;
import java.lang.reflect.Member;
import java.util.ArrayList;

public class RepoService {
    // initialize and read data from svc files
    public ArrayList<OrderService> readOrderList() {
        String orderItem = "";
        ArrayList<OrderService> orderList = new ArrayList<>();
        try {
            BufferedReader orderReader = new BufferedReader(new FileReader("repo/Order.csv"));
            while (orderItem != null) {
                if ((orderItem = orderReader.readLine()) == null) {
                    continue;
                }
                String[] singleItem = orderItem.split(",");
                OrderService newOrder =
                        new OrderService(
                                Integer.parseInt(singleItem[0]),
                                Integer.parseInt(singleItem[1]),
                                Boolean.parseBoolean(singleItem[2]),
                                singleItem[3]);
                orderList.add(newOrder);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return orderList;
    }

    public ArrayList<ProductService> readProductList() {
        String productItem = "";
        ArrayList<ProductService> productList = new ArrayList<>();
        try {
            BufferedReader productReader = new BufferedReader(new FileReader("repo/Products.csv"));
            while (productItem != null) {
                if ((productItem = productReader.readLine()) == null) {
                    continue;
                }
                String[] singleItem = productItem.split(",");
                ProductService newProduct =
                        new ProductService(
                                Integer.parseInt(singleItem[0]),
                                singleItem[1],
                                singleItem[2],
                                singleItem[3],
                                Float.parseFloat(singleItem[4]));
                productList.add(newProduct);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return productList;
    }

    public ArrayList<MemberService> readUserList() {
        String userItem = "";
        ArrayList<MemberService> memberList = new ArrayList<>();
        try {
            BufferedReader userReader = new BufferedReader(new FileReader("repo/User.csv"));
            while (userItem != null) {
                if ((userItem = userReader.readLine()) == null) {
                    continue;
                }
                String[] singleItem = userItem.split(",");
                MemberService newUser =
                        new MemberService(
                                Integer.parseInt(singleItem[0]),
                                singleItem[1],
                                singleItem[2],
                                singleItem[3],
                                singleItem[4],
                                singleItem[5],
                                Float.parseFloat(singleItem[6]));
                memberList.add(newUser);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return memberList;
    }

    public void writeIntoProductFile(ArrayList<ProductService> ProductList, boolean append) {
        try {
            BufferedWriter DataWriter =
                    new BufferedWriter(new FileWriter("repo/Products.csv", append));
            for (ProductService product : ProductList) {
                DataWriter.write(product.toDataLine());
            }
            DataWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeIntoUserFile(ArrayList<MemberService> MemberList, boolean append) {
        try {
            BufferedWriter DataWriter =
                    new BufferedWriter(new FileWriter("repo/User.csv", append));
            for (MemberService member : MemberList) {
                DataWriter.write(member.toDataLine());
            }
            DataWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeIntoOrderFile(ArrayList<OrderService> OrderList, boolean append) {
        try {
            BufferedWriter DataWriter =
                    new BufferedWriter(new FileWriter("repo/Order.csv", append));
            for (OrderService order : OrderList) {
                DataWriter.write(order.toDataLine());
            }
            DataWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void writeIntoFile(String fileName, String data, boolean append) {
        try {
            BufferedWriter DataWriter = new BufferedWriter(new FileWriter(fileName, append));
            DataWriter.write(data);
            DataWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
