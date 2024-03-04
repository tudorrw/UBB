import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BorrowedBooksFileReader borrowedBooksFileReader = new BorrowedBooksFileReader();
        ArrayList<BorrowedBook> borrowedBooks = borrowedBooksFileReader.getBorrowedBooks("borrowed_books.csv");
        System.out.println(borrowedBooks);

        Streams streams = new Streams();
        streams.displayGenreAndNumberOfBooks(borrowedBooks);
        streams.saveToFile(borrowedBooks);

        Thread1 thread1 = new Thread1(borrowedBooks);
        Thread2 thread2 = new Thread2(thread1);
        thread1.start();
        thread2.start();
    }
}