public class Section {
    private final int index;
    private final int[] shelves;

    public Section(int index, int[] shelves) {
        this.index = index;
        this.shelves = shelves;
    }

    public synchronized boolean hasBooks(Book book) {
        for (int i = index * 100; i < (index + 1) * 100; i++) {
            if (shelves[i] == book.ordinal() + 1) {
                return true;
            }
        }
        return false;
    }

    public synchronized boolean takeBook(Book book) {
        for (int i = index * 100; i < (index + 1) * 100; i++) {
            if (shelves[i] == book.ordinal() + 1) {
                shelves[i] = 0;
                return true;
            }
        }
        return false;
    }

    public synchronized boolean putBook(Book book) {
        for (int i = index * 100; i < (index + 1) * 100; i++) {
            if (shelves[i] == 0) {
                shelves[i] = book.ordinal() + 1;
                return true;
            }
        }
        return false;
    }
}
