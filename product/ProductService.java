package product;

import error.InvalidExceptionOption;
import repo.RepoService;
import tableFormatter.TableFormatterService;
import utils.Convert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ProductService {
    private static final String[] labelFields = {
        "Product ID", "Product Name", "Product Category", "Description", "Price"
    };
    private static RepoService repo;
    private static Scanner scanner;
    private String productID;
    private String productName;
    private String productCategory;
    private String description;
    private double price;

    public ProductService() {
        this.productID = "UNKNOWN";
        this.productName = "UNKNOWN";
        this.productCategory = "UNKNOWN";
        this.description = "UNKNOWN";
        this.price = 0;
    }

    public ProductService(RepoService repo, Scanner scanner) {
        this.productID = "UNKNOWN";
        this.productName = "UNKNOWN";
        this.productCategory = "UNKNOWN";
        this.description = "UNKNOWN";
        this.price = 0;
        if (ProductService.repo == null) ProductService.repo = repo;
        if (ProductService.scanner == null) ProductService.scanner = scanner;
    }

    public ProductService(
            String productID,
            String productName,
            String productCategory,
            String description,
            double price) {
        try {
            this.productID = productID;
            this.productName = productName;
            this.productCategory = productCategory;
            this.description = description;
            this.price = price;
        } catch (Exception e) {
            System.out.println(e + "Invalid DataType");
        }
    }

    public static String[] getLabelFields() {
        return labelFields;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String getProductID() {
        return productID;
    }

    public String[] getProduct() {
        return new String[] {
            (this.productID),
            this.productName,
            this.productCategory,
            this.description,
            Double.toString(this.price)
        };
    }

    public void showProductDetail() {
        System.out.println(
                "Product ID: "
                        + this.productID
                        + "\nProductName: "
                        + this.productName
                        + "\nProductCategory: "
                        + this.productCategory
                        + "\nDescription: "
                        + this.description
                        + "\nPrice: "
                        + this.price);
    }

    public String[] toProductRow() {
        return new String[] {
            String.valueOf(this.productID),
            this.productName,
            this.productCategory,
            this.description,
            Convert.toDecimal(this.price)
        };
    }

    public String toDataLine() {
        return this.productID
                + ","
                + this.productName
                + ","
                + this.productCategory
                + ","
                + this.description
                + ","
                + Convert.toDecimal(this.price)
                + "\n";
    }

    public void viewAllProducts() {
        // working
        ArrayList<ProductService> ProductList = repo.readProductList();
        TableFormatterService tableFormatter =
                new TableFormatterService(ProductService.getLabelFields());
        for (ProductService product : ProductList) {
            if (product.getProductID().equals("UNKNOWN")) continue;
            tableFormatter.addRows(product.getProduct());
        }
        tableFormatter.display();
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public void viewAllProductsByPrice() {
        Scanner snc = new Scanner(System.in);
        ArrayList<ProductService> sortedProducts;
        try {
            System.out.println(
                    "What do you want to sort by ? Type A (Ascending) or D (Descending)");
            String choice = snc.nextLine();
            sortedProducts = repo.readProductList();
            if (choice.equalsIgnoreCase("A")) {
                sortedProducts.sort(new SortPriceASC());
            } else if (choice.equalsIgnoreCase("D")) {
                sortedProducts.sort(new SortPriceDESC());
            } else {
                throw new InvalidExceptionOption("Invalid option! Try again");
            }
            TableFormatterService productTable =
                    new TableFormatterService(ProductService.getLabelFields());
            for (ProductService productService : sortedProducts) {
                if (productService.getProductID().equals("UNKNOWN")) continue;
                productTable.addRows(productService.getProduct());
            }
            productTable.display();
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchProductBasedOnCategories() {
        Scanner snc = scanner;
        String choice;
        ArrayList<ProductService> productListBySearch = new ArrayList<>();
        ArrayList<ProductService> ProductList = repo.readProductList();
        try {
            System.out.println(
                    "What do you want to search by products category? Enter the category name");
            choice = snc.nextLine();
            for (ProductService productService : ProductList) {
                if (productService.getProductCategory().equalsIgnoreCase(choice)) {
                    if (productService.getProductID().equals("UNKNOWN")) continue;
                    productListBySearch.add(productService);
                }
            }
            TableFormatterService productTable =
                    new TableFormatterService(ProductService.getLabelFields());
            for (ProductService productService : productListBySearch) {
                productTable.addRows(productService.getProduct());
            }
            if (productListBySearch.size() > 0) {
                productTable.display();
            } else {
                System.out.println("Sorry! Cant find any results for '" + choice + "' category");
            }
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
    }

    public void addNewProduct() {
        // Initialize
        System.out.println("-- Add new product --\n");
        Scanner scan = scanner;

        String productID;
        String productName;
        String productCategory;
        String description;
        double price;
        // Input
        try {
            System.out.println("Enter product id: ");
            productID = String.valueOf(scan.nextInt());
            scan.nextLine();
            System.out.println("Enter product name: ");
            productName = scan.nextLine();
            System.out.println("Enter product category: ");
            productCategory = scan.nextLine();
            System.out.println("Enter product description: ");
            description = scan.nextLine();
            System.out.println("Enter product price: ");
            price = scan.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Your input is invalid.\nReturning back...");
            return;
        }

        ProductService temp =
                new ProductService(productID, productName, productCategory, description, price);
        repo.writeIntoFile("repo/Products.csv", temp.toDataLine(), true);
        System.out.println("A new product has been added.\nReturning back...");
    }

    public void removeProduct() {
        System.out.println("-- Remove product price by ID --\n");
        Scanner scan = scanner;
        String choice;
        int removeProductIndex = -1;
        String productID;
        ArrayList<ProductService> ProductList = repo.readProductList();

        // input
        try {
            System.out.print("Enter product id to remove: ");
            productID = String.valueOf(scan.nextInt());
            scan.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Your input is invalid.\nReturning back...");
            return;
        }

        // Process
        for (ProductService productService : ProductList) {
            if (productID.equals(productService.getProductID())) {
                System.out.println();
                productService.showProductDetail();
                System.out.println();
                do {
                    System.out.print(
                            "Do you want to remove this product? It will be permanently delete!! (Y/N)? ");
                    choice = scan.next();
                } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"));
                if (choice.equalsIgnoreCase("y")) {
                    removeProductIndex = ProductList.indexOf(productService);
                    // working
                    //                    ProductList.remove(removeProductIndex);
                    ProductList.set(removeProductIndex, new ProductService());
                    repo.writeIntoProductFile(ProductList, false);
                    System.out.println("The product list has been updated. Returning back...");
                } else {
                    System.out.println("Cancel the action.\nReturning back...");
                    return;
                }
                break;
            }
        }
        if (removeProductIndex == -1) {
            System.out.println("Could not find your desired product.\nReturning back...");
        }
    }

    public void updatePrice() {
        System.out.println("-- Update product price by ID --\n");
        Scanner scan = scanner;

        // Input
        String productID;
        try {
            System.out.print("Enter your desired product id: ");
            productID = String.valueOf(scan.nextInt());
        } catch (InputMismatchException e) {
            System.out.println("Your input is invalid.\nReturning back...");
            return;
        }
        System.out.println();
        String res = null;

        ArrayList<ProductService> ProductList = repo.readProductList();
        // Process
        for (ProductService product : ProductList) {
            if (productID.equals(product.getProductID())) {
                product.showProductDetail();
                System.out.println();

                do {
                    System.out.print("Is this your desired product (Y/N)? ");
                    res = scan.next().toLowerCase();
                } while (!res.equals("y") && !res.equals("n"));

                if (res.equals("y")) {
                    double newPrice;
                    try {
                        System.out.print("Enter your desired price for this product: ");
                        newPrice = scan.nextDouble();
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

    @SuppressWarnings("InnerClassMayBeStatic")
    class SortPriceASC implements Comparator<ProductService> {
        public int compare(ProductService a, ProductService b) {
            return (int) a.getPrice() - (int) b.getPrice();
        }
    }

    @SuppressWarnings("InnerClassMayBeStatic")
    class SortPriceDESC implements Comparator<ProductService> {
        public int compare(ProductService a, ProductService b) {
            return (int) b.getPrice() - (int) a.getPrice();
        }
    }
}
