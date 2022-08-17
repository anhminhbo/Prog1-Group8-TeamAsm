package menu;

import User.MemberService;
import product.ProductService;
import tableFormatter.TableFormatterService;
import utils.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    protected ArrayList<ProductService> ProductList;
    protected ArrayList<MemberService> MemberList;
    protected MemberService CurrentUser;
    protected final List<Option> options = new ArrayList<>();

    protected void addOption(Option option) {
        options.add(option);
    }

    protected void displayOptions() {
        TableFormatterService table = new TableFormatterService(Option.getFields());
        for (Option option : options) {
            table.addRows(option.toStringArray());
        }
        table.display();
    }

    public void setCurrentUser(MemberService currentUser) {
        CurrentUser = currentUser;
    }

    public void run() {
        welcome();
        Scanner sc = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            displayOptions();
            System.out.print("Enter an option: ");
            String input = sc.next();
            for (Option option : options) {
                if (option.getToggleKey().equals(input)) {
                    option.execute();
                }
            }
//            System.out.println("Invalid input. Please try again.");
        }
    }

    private void welcome() {
        printDelimiter();
        System.out.println("""
                COSC2081 GROUP ASSIGNMENT\s
                STORE ORDER MANAGEMENT SYSTEM\s
                Instructor: Mr. Minh Vu\s
                Group: Hyper mega super ultra extra giga OOP java team\s
                s3891909, Truong Bach Minh\s
                s3924577, Nguyen Nguyen Khuong\s
                s3927201, Tran Ngoc Khang\s
                s3931605, Nguyen Cuong Anh Minh""");
        printDelimiter();
    }

    private void printDelimiter() {
        System.out.println("---------------------------------------------------------------------------------------------");
    }
}

