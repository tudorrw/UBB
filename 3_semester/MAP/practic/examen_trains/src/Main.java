import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        TrainEntryFileReader trainEntryFileReader = new TrainEntryFileReader();
        ArrayList<TrainEntry> trainEntries = trainEntryFileReader.getTrainEntries("train_schedule.csv");
        System.out.println(trainEntries);

        TrainEntryStreams streams = new TrainEntryStreams();
        streams.displayDestinationAndNumberOfTrains(trainEntries);
        streams.saveDelayedTrains(trainEntries);

        Train1 train1 = new Train1();
        Train2 train2 = new Train2();

        train1.start();
        train2.start();
    }
}