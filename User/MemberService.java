package User;

import constant.Membership;
import repo.RepoService;
import tableFormatter.TableFormatterService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MemberService {
    private static final String[] labelFields = {
        "Member ID", "User Name", "Password", "Full Name", "Phone Number", "Membership"
    };
    private static RepoService repo;
    private int memberID;
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String memberShip;

    public MemberService(RepoService repo) {
        if (MemberService.repo == null) MemberService.repo = repo;
    }

    public MemberService(
            int memberID, String userName, String password, String fullName, String phoneNumber) {
        // validation check
        //        if (!memberShip.equalsIgnoreCase("Gold")
        //                && !memberShip.equalsIgnoreCase("Silver")

        //                && !memberShip.equalsIgnoreCase("Platinum")
        //                && !memberShip.equalsIgnoreCase("None")) {
        //            throw new WrongInputRole("The membership must be Silver|Gold|Platinum|None");
        //        }
        //        if (!role.equalsIgnoreCase(Role.MEMBER) && !role.equalsIgnoreCase(Role.ADMIN)) {
        //            throw new WrongInputRole("Two roles expected Member or Admin");
        //        }

        this.memberID = memberID;
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.memberShip = Membership.None;
    }

    public MemberService(
            int memberID,
            String userName,
            String password,
            String fullName,
            String phoneNumber,
            String memberShip) {
        // validation check
        //        if (!memberShip.equalsIgnoreCase("Gold")
        //                && !memberShip.equalsIgnoreCase("Silver")

        //                && !memberShip.equalsIgnoreCase("Platinum")
        //                && !memberShip.equalsIgnoreCase("None")) {
        //            throw new WrongInputRole("The membership must be Silver|Gold|Platinum|None");
        //        }
        //        if (!role.equalsIgnoreCase(Role.MEMBER) && !role.equalsIgnoreCase(Role.ADMIN)) {
        //            throw new WrongInputRole("Two roles expected Member or Admin");
        //        }

        this.memberID = memberID;
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.memberShip = memberShip;
    }

    public static String[] getLabelFields() {
        return labelFields;
    }

    public ArrayList<MemberService> getMemberList() {
        return repo.readUserList();
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMemberShip() {
        return memberShip;
    }

    public void setMemberShip(String memberShip) {
        this.memberShip = memberShip;
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
                + this.memberShip;
    }

    public String[] getMember() {
        return new String[] {
            Integer.toString(this.memberID),
            this.userName,
            this.password,
            this.fullName,
            this.phoneNumber,
            this.memberShip
        };
    }

    public void viewAllMembers() {
        ArrayList<MemberService> memberList = repo.readUserList();
        TableFormatterService tableFormatter =
                new TableFormatterService(MemberService.getLabelFields());
        for (MemberService member : memberList) {
            if (member.getUserName().equals("admin")) continue;
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
