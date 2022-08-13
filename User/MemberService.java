package User;

import constant.Role;
import error.WrongInputRole;

public class MemberService {
    private static final String[] labelFields = {
        "Member ID", "User Name", "Password", "Full Name", "Phone Number", "Role", "Membership"
    };
    private final int memberID;
    private final String userName;
    private final String password;
    private final String fullName;
    private final String phoneNumber;
    private final String role;
    private final String memberShip;

    public MemberService(
            int memberID,
            String userName,
            String password,
            String fullName,
            String phoneNumber,
            String role,
            String memberShip)
            throws Exception {
        // validation check
        if (!memberShip.equalsIgnoreCase("Gold")
                && !memberShip.equalsIgnoreCase("Silver")
                && !memberShip.equalsIgnoreCase("Platinum")
                && !memberShip.equalsIgnoreCase("None")) {
            throw new WrongInputRole("The membership must be Silver|Gold|Platinum|None");
        }
        if (!role.equalsIgnoreCase(Role.MEMBER) && !role.equalsIgnoreCase(Role.ADMIN)) {
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

    public static String[] getLabelFields() {
        return labelFields;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public String getMemberShip() {
        return memberShip;
    }

    public String toDataLine() {
        return this.memberID
                + ","
                + this.userName
                + ","
                + this.password
                + ","
                + this.fullName
                + ","
                + this.phoneNumber
                + ","
                + this.role
                + ","
                + this.memberShip;
    }

    public String[] getMember() {
        return new String[] {
            Integer.toString(this.memberID),
            this.userName,
            this.password,
            this.fullName,
            this.phoneNumber,
            this.role,
            this.memberShip
        };
    }
}
