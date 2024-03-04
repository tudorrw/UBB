import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MenuFileReader {
    public ArrayList<MenuItem> getMenuItemsFromFile(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .skip(1)
                .map(MenuItem::parseString)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
