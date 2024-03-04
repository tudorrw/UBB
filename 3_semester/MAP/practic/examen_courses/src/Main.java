import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<CourseRegistration> courseRegistrations=getObjekts();

        showCoursesSorted(courseRegistrations);

        saveFailedStudents(courseRegistrations);

        testPattern();

        threads(courseRegistrations);
    }



    public static ArrayList<CourseRegistration> getObjekts() throws IOException {
        ArrayList<CourseRegistration> objekts = Files.lines(Paths.get("course_registration.csv")).skip(1).map(
                (line) -> { String[] attributes = line.split("_");
                            return new CourseRegistration(Integer.parseInt(attributes[0]),attributes[1],Integer.parseInt(attributes[2]),Integer.parseInt(attributes[3]),attributes[4]);}
        ).collect(Collectors.toCollection(ArrayList::new));

        return objekts;
    }



    public static void showCoursesSorted(List<CourseRegistration> courses){
//       Map<String,Long> map=courses.stream().collect(Collectors.groupingBy(CourseRegistration::getCourse_code,Collectors.counting()));
//        System.out.println(map);

        courses.stream().collect(Collectors.groupingBy(CourseRegistration::getCourse_code,Collectors.counting())).entrySet().stream().sorted((a,b)-> {return a.getValue().compareTo(b.getValue())*-1;}).forEach((a)-> System.out.println(a));
//        courses.stream().collect(Collectors.groupingBy(CourseRegistration::getCourse_code,Collectors.counting())).entrySet().stream().sorted().forEach((a)-> System.out.println(a));
    }

    public static void saveFailedStudents(List<CourseRegistration> courseRegistrations) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("failed_students.txt"));
        courseRegistrations.stream().filter((a)->{return a.getStatus().equals("Failed");}).map(CourseRegistration::getStudent_id).forEach((a)->{
            try {
                bw.write(String.valueOf(a));
                bw.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        bw.close();
    }

    //Am folosit strategy pattern, pattern-urile fiind CourseCoordinator si SubstituteTeacher metoda comuna fiind definita de interfata comuna
    //Desi ar mai fi fost posibil si un adapter pattern
    public static void testPattern(){
        ClassStudents classStudents = new ClassStudents();
        CourseCoordinator courseCoordinator = new CourseCoordinator();
        SubstituteTeacher substituteTeacher = new SubstituteTeacher();

        classStudents.setCoordinator(courseCoordinator);

        assert classStudents.getCoordinated().equals("Course coordinator is coordinating");


        classStudents.setCoordinator(substituteTeacher);
        assert classStudents.getCoordinated().equals("Substitute Teacher is coordinating");

    }


    public static void threads(List<CourseRegistration> courseRegistrations) throws InterruptedException {
        List<CourseRegistration> courseRegistrationListvol = courseRegistrations;
        AdderThread thread1 = new AdderThread(courseRegistrationListvol);
        CheckerThread thread2 = new CheckerThread(courseRegistrationListvol);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}