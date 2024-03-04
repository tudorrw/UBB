public class Train1 extends Thread {
    @Override
    public void run() {
        while (true) {
            System.out.println("Train-1: Arrived at the platform! Waiting 2 seconds before departure");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Train-1: Leaving platform!");
            try {
                Thread.sleep(1);
                for (int i = 1; i <= 2; i++) {
                    System.out.println("Train-1: Waiting for platform to be free");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
