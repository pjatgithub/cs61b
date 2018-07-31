package synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

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

    /**
     * Creates an instance of {@link Iterator Iterator} to pass through all
     * elements in the queue.
     *
     * @return an instance of {@link Iterator Iterator}.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    /**
     * {@code ArrayRingBufferIterator} implements {@link Iterator Iterator} and
     * is usded to pass through all elements in the corresponding instance of
     * {@link ArrayRingBuffer ArrayRingBuffer}.
     */
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int ct;
        private int index;

        /**
         * Creates an instance of {@link ArrayRingBufferIterator ArrayRingBufferIterator}.
         */
        ArrayRingBufferIterator() {
            ct = 0;
            index = first;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return ct < fillCount();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T item = rb[index];
            ct++;
            index = index(index + 1);

            return item;
        }
    }
}
