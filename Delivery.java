import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Delivery {
    private static final int NUM_BOOKS_DELIVERED = 10;
    private static final int DELIVERY_FREQUENCY = 100;

    private final Random random;
    private final Map<Book, Integer> books;

    public Delivery() {
        this.random = new Random();
        this.books = new HashMap<>();
        this.books.put(Book.FICTION, 0);
        this.books.put(Book.HORROR, 0);
        this.books.put(Book.ROMANCE, 0);
        this.books.put(Book.FANTASY, 0);
        this.books.put(Book.POETRY, 0);
        this.books.put(Book.HISTORY, 0);
    }

    public void deliverBooks() {
        for (int i = 0; i < NUM_BOOKS_DELIVERED; i++) {
            Book book = getRandomBook();
            this.books.put(book, this.books.get(book) + 1);
        }
    }

    private Book getRandomBook() {
        int index = random.nextInt(Book.values().length);
        return Book.values()[index];
    }

    public Map<Book, Integer> getBooks() {
        return this.books;
    }
}
