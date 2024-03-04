public class DiseaseThread extends Thread {
    private Animal animal;

    public DiseaseThread(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (animal) {
                if(animal.getHealth_status().equals(Health_status.Healthy)) {
                    System.out.println("Disease: Waiting 2 seconds");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Disease: Making " + animal.getName() + " sick");
                    animal.setHealth_status(Health_status.Sick);
                }
            }
        }
    }
}










