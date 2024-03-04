public class ClassStudents {
    private CoordinationStrategy coordinator;

    public ClassStudents() {
    }

    public void setCoordinator(CoordinationStrategy coordinator) {
        this.coordinator = coordinator;
    }

    public String getCoordinated(){
        return coordinator.coordinate();
    }
}
