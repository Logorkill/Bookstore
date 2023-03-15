import java.util.Random;

public class Assistant implements Runnable {
    
    private Bookstore bookstore;
    private int[] books;
    private int sectionIndex;

    public Assistant(Bookstore bookstore) {
        this.bookstore = bookstore;
        this.books = null;
        this.sectionIndex = -1;
    }

    public void run() {
        while (true) {
            if (this.books == null) {
                // No books to stock, wait for a new delivery
                synchronized (this.bookstore) {
                    this.bookstore.addAssistantToQueue(this);
                    try {
                        this.bookstore.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                this.books = this.bookstore.takeBooksFromDelivery();
            } else if (this.sectionIndex < 0) {
                // Choose a random section to stock
                Random rand = new Random();
                this.sectionIndex = rand.nextInt(this.bookstore.getNumSections());
            } else {
                // Walk to the section and stock the books
                Section section = this.bookstore.getSectionByIndex(this.sectionIndex);
                int numBooksToStock = Math.min(this.books.length, 10 - section.getNumBooks());
                for (int i = 0; i < numBooksToStock; i++) {
                    section.addBook(this.books[i]);
                    this.books[i] = 0;
                }
                try {
                    Thread.sleep(10 + 1 * numBooksToStock);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (section.getNumBooks() >= 100) {
                    // Section is full, reset assistant's state
                    this.books = null;
                    this.sectionIndex = -1;
                }
            }
        }
    }
}