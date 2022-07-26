import Menu.*;
import User.*;


public class Main {
    public static void main(String[] args) throws Exception {
        MainMenu menu = new MainMenu();
        for (int i = 0; i < menu.getProductList().length; i++) {
            System.out.println(menu.getProductList()[i].toString());
        }
    }
}