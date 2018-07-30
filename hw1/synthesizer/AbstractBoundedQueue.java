package synthesizer;

/**
 * {@code AbstractBoundedQueue} is used as a super class for any class implementing
 * {@link BoundedQueue BoundedQueue}.
 *
 * @param <T> type of elements put into the queue.
 */
public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T> {
    protected int fillCount;
    protected int capacity;

    /**
     * Returns maximum number of elements can be put.
     *
     * @return maximum number of elements can be put.
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * Returns number of items currently in the queue.
     *
     * @return number of items in the queue.
     */
    @Override
    public int fillCount() {
        return fillCount;
    }
}
