package repo;

import User.MemberService;
import product.ProductService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepoService {
    // initialize and read data from svc files
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
                                singleItem[5]);
                memberList.add(newUser);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return memberList;
    }

    public String[] readOrderList() {
        String[] array;
        try {
            List<String> listOfStrings = new ArrayList<>();
            BufferedReader bf = new BufferedReader(new FileReader("repo/Order.csv"));
            String line = bf.readLine();
            while (line != null) {
                listOfStrings.add(line);
                line = bf.readLine();
            }
            bf.close();
            array = listOfStrings.toArray(new String[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return array;
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
