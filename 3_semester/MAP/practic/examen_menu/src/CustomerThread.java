import java.util.List;

public class CustomerThread extends Thread {
    private List<MenuItem> orderList;

    public CustomerThread(List<MenuItem> orderList) {
        this.orderList = orderList;
    }
    @Override
    public void run() {
        for(int i = 1; i <= 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (orderList) {
                orderList.add( new MenuItem(i, "Item " + i));
                System.out.println("Customer: Placing order and waiting 1 second");
                if (i == 3) {
                    orderList.notify(); //notify chef thread
                }
            }
        }
        System.out.println("Customer thread finished placing orders!");
    }
}
