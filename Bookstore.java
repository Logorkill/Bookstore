import java.util.ArrayList;

public class Bookstore {

    private ArrayList<Assistant> queue;
    private int[] delivery;
    private int[] shelves;
    private int numSections;
    private int[] booksSold;
    private int revenue;

    public Bookstore(int numSections) {
        this.queue = new ArrayList<Assistant>();
        this.delivery = new int[10];
        this.shelves = new int[numSections * 100];
        this.numSections = numSections;
        this.booksSold = new int[numSections * 100];
        this.revenue = 0;
    }

    public int[] takeBooksFromDelivery() {
        int[] books = this.delivery;
        this.delivery = new int[10];
        return books;
    }

    public void addBooksToShelves(int[] books) {
        int index = 0;
        for (int i = 0; i < this.shelves.length; i++) {
            if (this.shelves[i] == 0) {
                this.shelves[i] = books[index];
                index++;
            }
            if (index >= books.length) {
                break;
            }
        }
    }

    public void addAssistantToQueue(Assistant assistant) {
        this.queue.add(assistant);
    }

    public Assistant getNextAssistantFromQueue() {
        Assistant assistant = null;
        if (!this.queue.isEmpty()) {
            assistant = this.queue.get(0);
            this.queue.remove(0);
        }
        return assistant;
    }

    public int getNumSections() {
        return this.numSections;
    }

    public Section getSectionByIndex(int index) {
        return new Section(index, this.shelves);
    }

    public synchronized void notifyAssistantFinished(Assistant assistant) {
        assistant.notify();
    }

    public synchronized void sellBook(Book book) {
        int index = book.getSectionIndex() * 100 + book.getShelfIndex();
        this.booksSold[index]++;
        this.revenue += book.getPrice();
        this.shelves[index] = 0;
        System.out.println("Bookstore sold book \"" + book.getTitle() + "\" for $" + book.getPrice() + ".");
    }

    public int getRevenue() {
        return this.revenue;
    }

    public int[] getBooksSold() {
        return this.booksSold;
    }

    public boolean hasDelivery() {
        for (int i = 0; i < delivery.length; i++) {
            if (delivery[i] != 0) {
                return true;
            }
        }
        return false;
    }

    public void addBookToDelivery(int book) {
        for (int i = 0; i < delivery.length; i++) {
            if (delivery[i] == 0) {
                delivery[i] = book;
                break;
            }
        }
    }
}