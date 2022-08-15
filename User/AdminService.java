package User;

import product.ProductService;
import repo.RepoService;
import tableFormatter.TableFormatterService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class AdminService {
    RepoService repo = new RepoService();
    public ArrayList<ProductService> ProductList = repo.readProductList();
    private String adminName;

    public static void main(String[] args) {
        AdminService admin = new AdminService();
        admin.addNewProduct();
        // admin.updatePrice();
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void viewAllProducts() {
        TableFormatterService tableFormatter =
                new TableFormatterService(ProductService.getLabelFields());
        for (ProductService product : ProductList) {
            tableFormatter.addRows(product.getProduct());
        }
        tableFormatter.display();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void addNewProduct() {
        // Initialize
        System.out.println("-- Add new product --\n");
        Scanner scan = new Scanner(System.in);

        int productID;
        String productName;
        String productCategory;
        String description;
        float price;
        // Input
        try {
            System.out.println("Enter product id: ");
            productID = scan.nextInt();
            scan.nextLine();
            System.out.println("Enter product name: ");
            productName = scan.nextLine();
            System.out.println("Enter product category: ");
            productCategory = scan.nextLine();
            System.out.println("Enter product description: ");
            description = scan.nextLine();
            System.out.println("Enter product price: ");
            price = scan.nextFloat();
        } catch (InputMismatchException e) {
            System.out.println("Your input is invalid.\nReturning back...");
            return;
        }

        ProductService temp =
                new ProductService(productID, productName, productCategory, description, price);
        repo.writeIntoFile("repo/Products.csv", temp.toDataLine(), true);
        ProductList = repo.readProductList();
        System.out.println("A new product has been added.\nReturning back...");
    }

    public void removeProduct() {
        System.out.println("-- Remove product price by ID --\n");
        Scanner scan = new Scanner(System.in);
        String choice;
        ProductService removeProduct = null;
        int productID;

        //input
        try {
            System.out.print("Enter product id to remove: ");
            productID = scan.nextInt();
            scan.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Your input is invalid.\nReturning back...");
            return;
        }

        //Process
        for (ProductService productService : ProductList) {
            if (productID == productService.getProductID()) {
                productService.showProductDetail();
                System.out.println();
            }
            do {
                System.out.print("Do you want to remove this product? It will be permanently delete!! (Y/N)? ");
                choice = scan.next();
            } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
            if (choice.equalsIgnoreCase("y")) {
                removeProduct = productService;
                ProductList.remove(productService);
                repo.writeIntoProductFile(ProductList, false);
                System.out.println("The product list has been updated. Returning back...");
            }
            else {
                System.out.println("Cancel the action.\nReturning back...");
                return;
            }
            break;
        }
        //change to ProductList and ProductList
        if (removeProduct == null) {
            System.out.println("Could not find your desired product.\nReturning back...");
        }
    }

    public void updatePrice() {
        System.out.println("-- Update product price by ID --\n");
        Scanner scan = new Scanner(System.in);

        // Input
        int productID;
        try {
            System.out.print("Enter your desired product id: ");
            productID = scan.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Your input is invalid.\nReturning back...");
            return;
        }
        System.out.println();
        String res = null;

        // Process
        for (ProductService product : ProductList) {
            if (productID == product.getProductID()) {
                product.showProductDetail();
                System.out.println();

                do {
                    System.out.print("Is this your desired product (Y/N)? ");
                    res = scan.next().toLowerCase();
                } while (!res.equals("y") && !res.equals("n"));
                if (res.equals("y")) {
                    float newPrice;
                    try {
                        System.out.print("Enter your desired price for this product: ");
                        newPrice = scan.nextFloat();
                    } catch (InputMismatchException e) {
                        System.out.println("Your input is invalid.\nReturning back...");
                        return;
                    }
                    product.setPrice(newPrice);
                } else {
                    System.out.println("Please try again.\nReturning back...");
                }

                break;
            }
        }
        if (res == null) {
            System.out.println("Could not find your desired product.\nReturning back...");
            return;
        }
        if (res.equals("y")) {
            repo.writeIntoProductFile(ProductList, false);
            System.out.println("The new price has been adjusted. Returning back...");
        }
    }

    public void viewAllMembers() {
        ArrayList<MemberService> memberList = repo.readUserList();
        TableFormatterService tableFormatter =
                new TableFormatterService(MemberService.getLabelFields());
        for (MemberService member : memberList) {
            tableFormatter.addRows(member.getMember());
        }
        tableFormatter.display();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
