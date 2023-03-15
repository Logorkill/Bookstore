import java.util.Random;

public class Customer implements Runnable {

    private Bookstore bookstore;
    private int[] sections;
    private int[] waitTimes;

    public Customer(Bookstore bookstore) {
        this.bookstore = bookstore;
        this.sections = new int[bookstore.getNumSections()];
        this.waitTimes = new int[bookstore.getNumSections()];
    }

    public void run() {
        while (true) {
            // Choose a random section to browse
            Random rand = new Random();
            int sectionIndex = rand.nextInt(this.bookstore.getNumSections());
            Section section = this.bookstore.getSectionByIndex(sectionIndex);

            if (section.getNumBooks() == 0) {
                // Section is empty, wait for a book to be stocked
                synchronized (section) {
                    long startTime = System.currentTimeMillis();
                    while (section.getNumBooks() == 0) {
                        try {
                            section.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.waitTimes[sectionIndex] += System.currentTimeMillis() - startTime;
                }
            }

            // Buy a book from the section
            int book = section.removeBook();
            this.sections[sectionIndex]++;

            // Update bookstore revenue
            this.bookstore.incrementRevenue(book);

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int[] getSections() {
        return this.sections;
    }

    public int[] getWaitTimes() {
        return this.waitTimes;
    }
}
