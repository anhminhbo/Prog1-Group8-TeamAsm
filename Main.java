import TableFormatter.*;
import Menu.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        //Create product table service
        MainMenu menu = new MainMenu();
        TableFormatterService productTable = new TableFormatterService(
                new String[]{"PRODUCT ID", "PRODUCT NAME", "PRODUCT CATEGORY",
                        "DESCRIPTION", "PRICE"}
        );
        for (int i = 0; i < menu.getProductList().size(); i++) {
            productTable.addRows(
                    menu.getProductList().get(i).getProduct()
            );
        }
        productTable.display();

        //Ex for table service

        TableFormatterService tableFormatterService = new TableFormatterService(
                new String[]{"EMPLOYEE ID", "NAME", "GENDER",
                        "AGE", "E-MAIL ID", "SALARY"});
        tableFormatterService.addRows(new String[]{
                        "B065", "Emma", "F", "21", "emma123@yahoo.com", "1"
                }
        );
        tableFormatterService.addRows(new String[]{
                        "B066", "Emma1", "F1", "211", "emma1231@yahoo.com", "11"
                }
        );
        tableFormatterService.addRows(new String[]{
                        "B067", "Emma2", "F2", "212", "emma1232@yahoo.com", "12"
                }
        );
        tableFormatterService.addRows(new String[]{
                        "B068", "Emma3", "F3", "213", "emma1233@yahoo.com", "13"
                }
        );
        tableFormatterService.addRows(new String[]{
                        "B069", "Emma4", "F4", "214", "emma1234@yahoo.com", "14"
                }
        );
        tableFormatterService.display();
        menu.register();
    }

}