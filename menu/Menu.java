package menu;

import repo.RepoService;
import tableFormatter.TableFormatterService;
import utils.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final RepoService repo = new RepoService();
    private final List<Option> options = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    
    public Scanner getScanner() {
        return scanner;
    }
    
    public RepoService getRepo() {
        return repo;
    }
    
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


    public void run() {
        welcome();
        //noinspection InfiniteLoopStatement
        while (true) {
            displayOptions();
            System.out.print("Enter an option: ");
            String input = String.valueOf(scanner.nextInt());
            scanner.nextLine();
            for (Option option : options) {
                if (option.getToggleKey().equals(input)) {
                    option.execute();
                }
            }
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
