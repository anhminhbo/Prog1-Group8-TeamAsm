package User;

//import Menu.*;
//import com.sun.tools.javac.Main;

public class MemberService {
    private final int memberID;
    private final String userName;
    private final String password;
    private final String fullName;
    private final int phoneNumber;
    private final String role;
    private final String memberShip;

    public MemberService(int memberID, String userName, String password, String fullName, int phoneNumber, String role, String memberShip) throws Exception{
        //validation check
        if(!memberShip.equals("Gold") && !memberShip.equals("Silver") && !memberShip.equals("Platinum")){
            throw new Exception();
        }
        if (!role.equals("member") && !role.equals("admin")){
            throw new Exception();
        }
        this.memberID = memberID;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.memberShip = memberShip;
    }

    public int getMemberID() {
        return memberID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public String getMemberShip() {
        return memberShip;
    }

    @Override
    public String toString() {
        return this.memberID + "," + this.userName + "," + this.password  + "," + this.fullName + "," + this.phoneNumber + "," + this.role + "," + this.memberShip;
    }
}
