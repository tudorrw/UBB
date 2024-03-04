import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        AnimalFileReader animalFileReader = new AnimalFileReader();
        ArrayList<Animal> animals = animalFileReader.getAnimals("animals_inventory.csv");
        System.out.println(animals);

        Streams streams = new Streams();
        streams.displaySpeciesAndNumberOfAnimals(animals);
        streams.saveSickAnimalsToFile(animals);

        AnimalService animalCareTaker = new AnimalCareTaker();
        animalCareTaker.steal();

        DoctorThread doctor = new DoctorThread(animals.get(1));
        DiseaseThread disease = new DiseaseThread(animals.get(1));
        doctor.start();
        disease.start();



    }

}