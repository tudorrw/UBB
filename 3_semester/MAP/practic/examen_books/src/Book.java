public class Book {
    private String title;
    private String author;
    private static Book instance;
    private Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
    public static Book getInstance(String title, String author) {
        if(instance == null || !instance.getAuthor().equals(author) || !instance.getTitle().equals(title)) {
            instance = new Book(title, author);
        }
        return instance;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
}
