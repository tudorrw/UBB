import java.util.List;

public class ChefThread extends Thread {
    private List<MenuItem> orderList;

    public ChefThread(List<MenuItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public void run() {
        synchronized (orderList) {
            while (orderList.isEmpty()) {
                try {
                    orderList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Chef: Preparing orders!");
            for(MenuItem item: orderList) {
                System.out.println("Chef: preparing " + item.getName());
            }
            orderList.clear();
        }
        System.out.println("Chef finished preparing orders!");
    }
}
