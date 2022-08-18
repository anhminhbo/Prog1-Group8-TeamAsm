import menu.MainMenu;
import order.OrderService;
import repo.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.run();

//        RepoService repo = new RepoService();
//        ArrayList<OrderService> OrderList = repo.readOrderList();
//        System.out.println(OrderList);
//        for (OrderService order : OrderList) {
//            System.out.println(String.toString(order.isPaidStatus() order.getProductList()) + order.getCusID(), order.isPaidStatus() order.getProductList());
//        }
    }
}

