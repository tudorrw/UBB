import javax.management.timer.Timer;
import java.util.List;

public class AdderThread extends Thread{
    private volatile List<CourseRegistration> courseRegistrationList;

    public AdderThread(List<CourseRegistration> courseRegistrationList) {
        this.courseRegistrationList = courseRegistrationList;
    }

    @Override
    public void run(){
        for(int i=0;i<5;i++){

            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.courseRegistrationList.add(new CourseRegistration(1,"1",1,1,"Failed"));
            System.out.println("Thread 1 - Added new registration and waiting 2 seconds");


        }
    }
}
