package User;

public class User extends Customer {
    private String userName;
    private String password;
    private String role;
    private String memberShip;

    public User() {
        this.userName = "UNKNOWN";
        this.password = "UNKNOWN";
        this.role = "member";
        this.memberShip = "UNKNOWN";
    }

    public User(String userName, String password, String role, String memberShip) {
        this.userName = userName;
        this.password = password;
        this.role = "member";
        this.memberShip = memberShip;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMemberShip(String memberShip) {
        this.memberShip = memberShip;
    }


}
