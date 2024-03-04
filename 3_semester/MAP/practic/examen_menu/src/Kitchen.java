import java.util.HashMap;
import java.util.Map;

public class Kitchen {
    private Map<Integer, MenuItem> menu;
    public Kitchen() {
        menu = new HashMap<>();
        initialize();
    }

    public MenuItem prepareItem(int itemId) {
        if(menu.containsKey(itemId)) {
            MenuItem item = menu.get(itemId);
            if(item.isAvailability()) {
                System.out.println("Preparing " + item.getName());
                return item;
            }
            else {
                System.out.println(item.getName() + " is not available!");
                return null;
            }
        }
        else {
            System.out.println("Item not found in the menu!");
            return null;
        }
    }

    private void initialize() {
        MenuItem item1 = new MenuItem(1, "Burger", Category.Dinner, 10.99, true);
        MenuItem item2 = new MenuItem(2, "Pizza", Category.Lunch, 12.99, false);
        MenuItem item3 = new MenuItem(3, "Cheesecake", Category.Dessert,4.99, true);
        menu.put(item1.getItem_id(), item1);
        menu.put(item2.getItem_id(), item2);
        menu.put(item3.getItem_id(), item3);
    }
}
