import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class LandFileReader {
    public ArrayList<Land> getCountries(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .map(Land::parseString)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
