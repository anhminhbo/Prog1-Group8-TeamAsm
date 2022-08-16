package product;

public class ProductService {
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

    public ProductService(int productID, String productName, String productCategory, String description, float price) {
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

    public float getPrice() {
        return price;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public String[] getProduct() {
        return new String[]{
                Integer.toString(this.productID), this.productName, this.productCategory, this.description, Float.toString(this.price)
        };
    }

    @Override
    public String toString() {
        return "Product ID: " + this.productID + ", ProductName: " + this.productName + ", ProductCategory: " + this.productCategory + ", Description" + this.description + ", Price" + this.price;
    }
}
