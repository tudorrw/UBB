import java.util.List;

public class BorrowedBook {
    private String title;
    private String author;
    private String genre;
    private int borrowed_days;
    private int borrowed_id;

    public BorrowedBook(String title, String author, String genre, int borrowed_days, int borrowed_id) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.borrowed_days = borrowed_days;
        this.borrowed_id = borrowed_id;
    }
    public static BorrowedBook parseString(String row) {
        List<String> attributes = List.of(row.split(";"));
        return new BorrowedBook(
                attributes.get(0),
                attributes.get(1),
                attributes.get(2),
                Integer.parseInt(attributes.get(3)),
                Integer.parseInt(attributes.get(4))
        );
    }

    public String getGenre() {
        return genre;
    }

    public int getBorrowed_days() {
        return borrowed_days;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getBorrowed_id() {
        return borrowed_id;
    }

    @Override
    public String toString() {
        return title + " " + author + " " + genre + " " + borrowed_days + " " + borrowed_id;
    }
}
