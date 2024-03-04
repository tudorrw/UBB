import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TrainEntryStreams {
    public void displayDestinationAndNumberOfTrains(ArrayList<TrainEntry> trainEntries) {
        trainEntries.stream()
                .collect(Collectors.groupingBy(TrainEntry::getDestination, Collectors.counting()))
                .entrySet().stream()
                .sorted((a,b) -> a.getValue().compareTo(b.getValue()) * -1)
                .forEach(System.out::println);
    }

    public void saveDelayedTrains(ArrayList<TrainEntry> trainEntries) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("delayed_platform.txt"));
        trainEntries.stream()
                .filter(trainEntry -> trainEntry.getStatus().equals("Delayed"))
                .map(TrainEntry::getPlatform)
                .forEach(trainEntry -> {
                    try {
                        bw.write(trainEntry);
                        bw.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException();
                    }
                });
        bw.close();
    }
}
