package User;

import Error.*;

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
        if(!memberShip.equalsIgnoreCase("Gold") && !memberShip.equalsIgnoreCase("Silver") && !memberShip.equalsIgnoreCase("Platinum") && !memberShip.equalsIgnoreCase("None")){
            throw new WrongInputRole("The membership must be Silver|Gold|Platinum|None");
        }
        if (!role.equalsIgnoreCase("member") && !role.equalsIgnoreCase("admin")){
            throw new WrongInputRole("Two roles expected Member or Admin");
        }
        this.memberID = memberID;
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.role = role.trim();
        this.memberShip = memberShip.trim();
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
