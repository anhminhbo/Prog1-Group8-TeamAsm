package repo;

import User.MemberService;
import product.ProductService;

import java.io.*;
import java.util.ArrayList;

public class RepoService {
    //initialize and read data from svc files
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
                ProductService newProduct = new ProductService(Integer.parseInt(singleItem[0]), singleItem[1], singleItem[2], singleItem[3], Float.parseFloat(singleItem[4]));
                productList.add(newProduct);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return productList;
    }
    public ArrayList<MemberService> readUserList() {
        String userItem = "";
        ArrayList<MemberService> memberList= new ArrayList<>();
        try {
            BufferedReader userReader = new BufferedReader(new FileReader("repo/User.csv"));
            while (userItem != null) {
                if ((userItem = userReader.readLine()) == null) {
                    continue;
                }
                String[] singleItem = userItem.split(",");
                MemberService newUser = new MemberService(Integer.parseInt(singleItem[0]), singleItem[1], singleItem[2], singleItem[3], Integer.parseInt(singleItem[4]), singleItem[5], singleItem[6]);
                memberList.add(newUser);
            }
        } catch (Exception err) {
            err.printStackTrace();
        }
        return memberList;
    }
}
