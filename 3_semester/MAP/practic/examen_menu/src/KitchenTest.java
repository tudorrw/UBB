
public class KitchenTest {
    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen();
        MenuItem preparedItem = kitchen.prepareItem(2);
        if(preparedItem != null) {
            System.out.println("Preparation successful!");
        }
        else {
            System.out.println("Preparation failed!");
        }
        preparedItem = kitchen.prepareItem(3);
        if(preparedItem != null) {
            System.out.println("Preparation successful!");
        }
        else {
            System.out.println("Preparation failed!");
        }
    }
}