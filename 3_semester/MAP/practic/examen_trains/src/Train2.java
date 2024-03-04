public class Train2 extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                for (int i = 1; i <= 2; i++) {
                    System.out.println("Train-2: Waiting for platform to be free");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Train-2: Arrived at the platform! Waiting 2 seconds before departure");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Train-2: Leaving platform!");
        }
    }
}
