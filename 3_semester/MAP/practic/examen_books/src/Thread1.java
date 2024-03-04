import java.util.ArrayList;

public class Thread1 extends Thread{
    public ArrayList<BorrowedBook> borrowedBooks;

    public Thread1(ArrayList<BorrowedBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    @Override
    public void run() {
        synchronized (borrowedBooks) {
            for(BorrowedBook borrowedBook : borrowedBooks) {
                System.out.println("Thread-1: " + borrowedBook);
            }
        }
    }
}
