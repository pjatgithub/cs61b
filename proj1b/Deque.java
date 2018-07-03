/**
 * An interface for the deque data structure.
 *
 * @author Hongbin Jin
 * @param <T> type of the elements to be hold.
 */
public interface Deque<T> {
    /**
     * Adds an item of type T to the front of the deque.
     *
     * @param item the element to be added at the head the deque.
     */
    void addFirst(T item);

    /**
     * Adds an item of type T to the back of the deque.
     *
     * @param item the element to be added at the tail of the deque.
     */
    void addLast(T item);

    /**
     * Tests whether the deque is empty.
     *
     * @return true if deque is empty, false otherwise.
     */
    boolean isEmpty();

    /**
     * Returns the number of items in the deque.
     *
     * @return the numer of items in the deque.
     */
    int size();

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    void printDeque();

    /**
     * Removes and returns the item at the front of the deque.
     *
     * @return the element previously at the head of the deque,
     *         null if the deque is empty.
     */
    T removeFirst();

    /**
     * Removes and returns the item at the back of the deque.
     *
     * @return the element previously at the tail of the deque,
     *         null if the deque is empty.
     */
    T removeLast();

    /**
     * Gets the item at the given index, where 0 is the front,
     * 1 is the next item, and so forth.
     *
     * @param index the index of the element
     * @return the element at the given position, null if no such item.
     */
    T get(int index);
}
