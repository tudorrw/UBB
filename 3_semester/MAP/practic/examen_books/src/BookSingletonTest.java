public class BookSingletonTest {
    public static void main(String[] args) {
        Book book1 = Book.getInstance("Pet cemetery", "Stephen King");
        Book book2 = Book.getInstance("Pet cemetery", "Stephen King");

        System.out.println(book1);
        System.out.println(book2);
        System.out.println("book1 == book2: " + (book1 == book2));  // Should print true
    }
}
