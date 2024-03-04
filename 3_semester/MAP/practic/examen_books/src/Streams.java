import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Streams {
    public void displayGenreAndNumberOfBooks(ArrayList<BorrowedBook> borrowedBooks) {
        borrowedBooks.stream()
                .collect(Collectors.groupingBy(BorrowedBook::getGenre, Collectors.counting()))
                .entrySet().stream()
                .sorted((a,b) -> a.getValue().compareTo(b.getValue()) * -1)
                .forEach(System.out::println);
    }
    public void saveToFile(ArrayList<BorrowedBook> borrowedBooks) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("borrowed_long.txt"));
        borrowedBooks.stream()
                .filter(borrowedBook -> borrowedBook.getBorrowed_days() > 5)
                .map(borrowedBook -> borrowedBook.getTitle().toLowerCase())
                .forEach(borrowedBook -> {
                    try {
                        bw.write(borrowedBook);
                        bw.newLine();
                    } catch(IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        bw.close();
    }
}
