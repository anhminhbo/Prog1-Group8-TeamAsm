package User;

public class Administrator extends User{
    private String adminName;

    public Administrator(String userName, String password, String role, String memberShip, String adminName) {
        super(userName, password, role, memberShip);
        this.adminName = adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void login(){}
    public void viewProducts(){}
    public void viewOrders(){}
    public void viewMembers(){}
    public void addProducts(){}
    public void updatePrice(){}
    public void getAllOrdersByCusID(){}
    public void ChangeOrderStatus(){}
    public void CalculateDailyRevenue(){}
    public void CheckExecutedOrders(){}
}
