import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class BorrowedBooksFileReader {
    public ArrayList<BorrowedBook> getBorrowedBooks(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .skip(1)
                .map(BorrowedBook::parseString)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

