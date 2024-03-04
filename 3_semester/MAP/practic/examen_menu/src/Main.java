import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        MenuFileReader menuFileReader = new MenuFileReader();
        ArrayList<MenuItem> menuItems = menuFileReader.getMenuItemsFromFile("menu_items.csv");
//        System.out.println(menuItems);

        MenuItemStreams streams = new MenuItemStreams();
        streams.displayCategoriesAndNumberOfItems(menuItems);
        streams.saveToFile(menuItems);

//        factory design pattern :

        Kitchen kitchen = new Kitchen();
        MenuItem menuItem1 = kitchen.prepareItem(2);
        System.out.println(menuItem1);
        MenuItem menuItem2 = kitchen.prepareItem(3);
        System.out.println(menuItem2);
        MenuItem menuItem3 = kitchen.prepareItem(4);
        System.out.println(menuItem3);

    }
}