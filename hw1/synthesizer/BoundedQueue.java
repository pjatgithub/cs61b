package synthesizer;

/**
 * {@code BoundedQueue} is a special king of queue with the limited capacity
 *
 * @param <T> type of elements put into the queue.
 */
public interface BoundedQueue<T> extends Iterable<T> {
    /**
     * Returns maximum number of elements can be put.
     *
     * @return maximum number of elements can be put.
     */
    int capacity();

    /**
     * Returns number of items currently in the queue.
     *
     * @return number of items in the queue.
     */
    int fillCount();

    /**
     * Adds item {@code x} to the end of the queue.
     *
     * @param x item to be added.
     */
    void enqueue(T x);

    /**
     * Deletes and returns item from the front of the queue.
     *
     * @return item at the front of the queue.
     */
    T dequeue();

    /**
     * Returns (but does not delete) item from the front of the queue.
     *
     * @return item at the front of the queue.
     */
    T peek();

    /**
     * Tests whether the queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * Tests whether the queue is full.
     *
     * @return true if the queue is full, false otherwise.
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
