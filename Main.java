<<<<<<< HEAD
import Order.OrderService;
import TableFormatter.*;
import Menu.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        //Create product table service
        MainMenu menu = new MainMenu();
//        menu.run();
//        TableFormatterService productTable = new TableFormatterService(
//                new String[]{"PRODUCT ID", "PRODUCT NAME", "PRODUCT CATEGORY",
//                        "DESCRIPTION", "PRICE"}
//        );
//        for (int i = 0; i < menu.getProductList().size(); i++) {
//            productTable.addRows(
//                    menu.getProductList().get(i).getProduct()
//            );
//        }
//        productTable.display();

        //Ex for table service

=======
import menu.MainMenu;

public class Main {
    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.run();
>>>>>>> b6ba9edada7d173618ef569b8383d521060f4417
//        TableFormatterService tableFormatterService = new TableFormatterService(
//                new String[]{"EMPLOYEE ID", "NAME", "GENDER",
//                        "AGE", "E-MAIL ID", "SALARY"});
//        tableFormatterService.addRows(new String[]{
//                        "B065", "Emma", "F", "21", "emma123@yahoo.com", "1"
//                }
//        );
//        tableFormatterService.addRows(new String[]{
//                        "B066", "Emma1", "F1", "211", "emma1231@yahoo.com", "11"
//                }
//        );
//        tableFormatterService.addRows(new String[]{
//                        "B067", "Emma2", "F2", "212", "emma1232@yahoo.com", "12"
//                }
//        );
//        tableFormatterService.addRows(new String[]{
//                        "B068", "Emma3", "F3", "213", "emma1233@yahoo.com", "13"
//                }
//        );
//        tableFormatterService.addRows(new String[]{
//                        "B069", "Emma4", "F4", "214", "emma1234@yahoo.com", "14"
//                }
//        );
//        tableFormatterService.display();

        TableFormatterService productTable = new TableFormatterService(
                new String[]{"ORDER ID", "CUSTOMER ID", "PAID STATUS",
                        "PRODUCT LIST"}
        );
        for (int i = 0; i < menu.getOrderList().size(); i++) {
            productTable.addRows(
                    menu.getOrderList().get(i).getProduct()
            );
        }
        productTable.display();
    }

//    OrderService createOrder = new OrderService();

}