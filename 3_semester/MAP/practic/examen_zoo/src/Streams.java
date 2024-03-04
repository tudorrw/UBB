import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Streams {
    public void displaySpeciesAndNumberOfAnimals(ArrayList<Animal> animals) {
        animals.stream()
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()))
                .entrySet().stream()
                .sorted( (a,b) -> a.getValue().compareTo(b.getValue()) * -1)
                .forEach(System.out::println);
    }

    public void saveSickAnimalsToFile(ArrayList<Animal> animals) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("sick_animals.txt"));
        animals.stream()
                .filter(animal -> animal.getHealth_status().equals(Health_status.Sick))
                .map(Animal::getName)
                .forEach(animal -> {
                    try {
                        bw.write(animal);
                        bw.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        bw.close();
    }
}
