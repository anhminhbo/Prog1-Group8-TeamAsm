package user;

import constant.Membership;
import repo.RepoService;
import tableFormatter.TableFormatterService;
import utils.Convert;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MemberService {
    private static final String[] labelFields = {
        "Member ID", "User Name", "Password", "Full Name", "Phone Number", "Membership"
    };
    private static RepoService repo;
    private String memberID;
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String memberShip;
    private double accumulatedMoney;

    public MemberService(RepoService repo) {
        if (MemberService.repo == null) MemberService.repo = repo;
    }

    public MemberService(
            String memberID,
            String userName,
            String password,
            String fullName,
            String phoneNumber,
            double accumulatedMoney) {

        this.memberID = memberID.trim();
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.memberShip = Membership.None;
        this.accumulatedMoney = 0;
    }

    public MemberService(
            String memberID,
            String userName,
            String password,
            String fullName,
            String phoneNumber,
            String memberShip,
            double accumulatedMoney) {

        this.memberID = memberID;
        this.userName = userName.trim();
        this.password = password.trim();
        this.fullName = fullName.trim();
        this.phoneNumber = phoneNumber;
        this.memberShip = memberShip;
        this.accumulatedMoney = accumulatedMoney;
    }

    public static String[] getLabelFields() {
        return labelFields;
    }

    public ArrayList<MemberService> getMemberList() {
        return repo.readUserList();
    }

    public String getMemberID() {
        return memberID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberShip() {
        return memberShip;
    }

    public void setMemberShip(String memberShip) {
        this.memberShip = memberShip;
    }

    public double getAccumulatedMoney() {
        return accumulatedMoney;
    }

    public void updateAccumulatedMoney(double totalPriceOfOrder, boolean isAddition) {
        this.accumulatedMoney += (isAddition ? totalPriceOfOrder : -totalPriceOfOrder);
    }

    public void updateMemberShip() {
        if (accumulatedMoney > 25000000) {
            setMemberShip(Membership.Platinum);
        } else if (accumulatedMoney > 10000000) {
            setMemberShip(Membership.Gold);
        } else if (accumulatedMoney > 5000000) {
            setMemberShip(Membership.Silver);
        } else {
            setMemberShip(Membership.None);
        }
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
                + this.memberShip
                + ","
                + Convert.toDecimal(this.accumulatedMoney)
                + "\n";
    }

    private String[] getMember() {
        return new String[] {
            this.memberID,
            this.userName,
            this.password,
            this.fullName,
            this.phoneNumber,
            this.memberShip,
            Double.toString(this.accumulatedMoney)
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
