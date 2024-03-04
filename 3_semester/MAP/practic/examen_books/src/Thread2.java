import java.util.ArrayList;
import java.util.List;

public class Thread2 extends Thread{
    private Thread1 thread1;

    public Thread2(Thread1 thread1) {
        this.thread1 = thread1;
    }

    @Override
    public void run() {
        System.out.println("Thread-2: Start");
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Thread-2: Done");
    }
}
