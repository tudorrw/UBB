import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TrainEntryFileReader {
    public ArrayList<TrainEntry> getTrainEntries(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .skip(1)
                .map(TrainEntry::parseString)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
