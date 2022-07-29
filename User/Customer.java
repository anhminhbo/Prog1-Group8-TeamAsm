package User;

public class Customer {
    private int cusID;
    private String cusName;
    private String address;
    private String email;

    public Customer() {
        this.cusID = -1;
        this.cusName = "UNKNOWN";
        this.address = "UNKNOWN";
        this.email = "UNKNOWN";
    }

    public Customer(int cusID, String cusName, String address, String email) {
        this.cusID = cusID;
        this.cusName = cusName;
        this.address = address;
        this.email = email;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void register() {

    }

    public void login() {

    }

    public void viewInfo() {

    }

    public void viewProducts() {

    }

    public void searchForCategory() {

    }

    public void sortedByPrice() {
    }
}
