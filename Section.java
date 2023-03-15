import java.util.ArrayList;
import java.util.List;

public class Section {
    private final String name;
    private final List<Book> books;

    public Section(String name) {
        this.name = name;
        this.books = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public synchronized void stockBooks(List<Book> books) throws InterruptedException {
        System.out.println("<" + Bookstore.getTickCount() + "> <" + Thread.currentThread().getName() + "> began stocking " + name + " section with " + books.size() + " books");
        for (Book book : books) {
            Thread.sleep(1);
            this.books.add(book);
        }
        System.out.println("<" + Bookstore.getTickCount() + "> <" + Thread.currentThread().getName() + "> finished stocking " + name + " section with " + books.size() + " books");
    }

    public synchronized boolean hasBooks() {
        return !books.isEmpty();
    }

    public synchronized Book takeBook() throws InterruptedException {
        while (!hasBooks()) {
            wait();
        }
        Thread.sleep(1);
        Book book = books.remove(0);
        System.out.println("<" + Bookstore.getTickCount() + "> <" + Thread.currentThread().getName() + "> took a " + book.getGenre() + " book from " + name + " section");
        notifyAll();
        return book;
    }

    public synchronized void returnBook(Book book) {
        Thread.sleep(1);
        books.add(book);
        System.out.println("<" + Bookstore.getTickCount() + "> <" + Thread.currentThread().getName() + "> returned a " + book.getGenre() + " book to " + name + " section");
        notifyAll();
    }
}
