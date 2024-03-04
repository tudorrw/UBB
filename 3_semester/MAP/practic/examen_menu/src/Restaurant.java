import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    public static void main(String[] args) {
        List<MenuItem> orderList = new ArrayList<>();
        CustomerThread customerThread = new CustomerThread(orderList);
        ChefThread chefThread = new ChefThread(orderList);

        customerThread.start();
        chefThread.start();

        try {
            customerThread.join();
            chefThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
