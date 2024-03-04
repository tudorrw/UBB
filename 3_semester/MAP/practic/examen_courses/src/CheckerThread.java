import java.util.List;

public class CheckerThread extends Thread{
    private volatile List<CourseRegistration> courseRegistrations;
    public CheckerThread(List<CourseRegistration> courseRegistrations) {
        this.courseRegistrations=courseRegistrations;
    }

    @Override
    public void run(){
        int initial_length= courseRegistrations.size();
        int last_length = initial_length;
        while(last_length!=initial_length+5){
            if(last_length!=courseRegistrations.size()){
                System.out.println("Thread 2: New Registration");
                last_length+=1;
            }
        }
    }
}
