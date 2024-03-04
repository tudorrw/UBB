public class CourseRegistration {
    private int student_id;
    private String course_code;
    private int semester;
    private int grade;
    private String status;

    public CourseRegistration(int student_id, String course_code, int semester, int grade, String status) {
        this.student_id = student_id;
        this.course_code = course_code;
        this.semester = semester;
        this.grade = grade;
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseRegistration{" +
                "student_id=" + student_id +
                ", course_code='" + course_code + '\'' +
                ", semester=" + semester +
                ", grade=" + grade +
                ", status='" + status + '\'' +
                '}';
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
