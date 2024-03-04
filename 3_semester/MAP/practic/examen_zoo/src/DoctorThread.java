public class DoctorThread extends Thread {
    private Animal animal;

    public DoctorThread(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (animal) {
                if(animal.getHealth_status().equals(Health_status.Sick)) {
                    animal.setHealth_status(Health_status.Healthy);
                    System.out.println("Doctor: Healing animal");
                }
            }
        }
    }
}
