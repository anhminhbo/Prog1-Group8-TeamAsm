package product;

public class ProductService {
    private static final String[] labelFields = {
        "Product ID", "Product Name", "Product Category", "Description", "Price"
    };
    private int productID;
    private String productName;
    private String productCategory;
    private String description;
    private float price;

    public ProductService() {
        this.productID = -1;
        this.productName = "UNKNOWN";
        this.productCategory = "UNKNOWN";
        this.description = "UNKNOWN";
        this.price = 0;
    }

    public ProductService(
            int productID,
            String productName,
            String productCategory,
            String description,
            float price) {
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public int getProductID() {
        return productID;
    }

    public String[] getProduct() {
        return new String[] {
            Integer.toString(this.productID),
            this.productName,
            this.productCategory,
            this.description,
            Float.toString(this.price)
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

    public String toDataLine() {
        return this.productID
                + ","
                + this.productName
                + ","
                + this.productCategory
                + ","
                + this.description
                + ","
                + this.price
                + "\n";
    }
}
