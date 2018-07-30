package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    @SuppressWarnings("unchecked")
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        this.capacity = capacity;
        fillCount = 0;
        first = 0;
        last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        checkFull();

        rb[last] = x;
        last = index(last + 1);
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        checkEmpty();

        T item = rb[first];
        rb[first] = null;
        first = index(first + 1);
        fillCount--;

        return item;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        checkEmpty();

        return rb[first];
    }

    /**
     * Checks whether the queue if empty. If the queue if empty, a
     * {@link RuntimeException RuntimeException} is thrown.
     */
    private void checkEmpty() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
    }

    /**
     * Checks whether the queue is full. If the queue is full, a
     * {@link RuntimeException RuntimeException} is thrown.
     */
    private void checkFull() {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
    }

    /**
     * Returns a valid index for the given index.
     * @param i an index which may need to be warped around.
     * @return a valid index.
     */
    int index(int i) {
        int capacity = capacity();

        return i - capacity * Math.floorDiv(i, capacity);
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
}
