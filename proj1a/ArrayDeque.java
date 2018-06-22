/**
 * <code>ArrayDeque</code> implements the deque data structure with a dynamic array.
 *
 * @author Hongbin Jin
 * @param <T> type of elements to be held.
 */
public class ArrayDeque<T> {
    private static final int INITIAL_CAPACITY = 8;
    private static final int RESIZE_THRESHOLD = 16;
    private static final int RESIZE_FACTOR = 2;
    private static final double USAGE_RATIO_THRESHOLD = 0.25;
    private int capacity;
    private T[] items;
    private int size, nextHead, nextTail;

    /**
     * Initializes an empty array-based deque.
     */
    public ArrayDeque() {
        capacity = INITIAL_CAPACITY;
        items = (T[]) new Object[capacity + 1];
        size = 0;
        nextHead = items.length - 1;
        nextTail = 0;
    }

    /**
     * Adds an element at the head of the deque.
     * @param item an element to be added.
     */
    public void addFirst(T item) {
        checkResize();

        items[nextHead] = item;
        nextHead = index(nextHead - 1);
        size++;
    }

    /**
     * Adds an element at the tail of the deque.
     * @param item an element to be added.
     */
    public void addLast(T item) {
       checkResize();

       items[nextTail] = item;
       nextTail = index(nextTail + 1);
       size++;
    }

    /**
     * Tests whether the deque is empty.
     * @return true if empty, otherwise false.
     */
    public boolean isEmpty() {
        return size == 0;.
    }

    /**
     * Gets the number of elements held by the deque.
     * @return the number of elements held by the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the elements in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        for (int ct = 0; ct < size; ct++) {
            int i = index(nextHead + 1 + ct);
            System.out.printf("%s%s", items[i].toString(), ct == size - 1 ? "" : " ");
        }

        System.out.println();
    }

    /**
     * Removes and returns the element at the head of the deque.
     * @return the removed first element if existing, otherwise null.
     */
    public T removeFirst() {
        if (isEmpty())
            return null;

        checkResize();
        nextTail = index(nextTail - 1);
        T item = items[nextTail];
        items[nextTail] = null;

        return item;
    }

    /**
     * Removes and returns the element at the tail of the deque.
     * @return the removed last element if existing, otherwise null.
     */
    public T removeLast() {
        if (isEmpty())
            return null;

        checkResize();
        nextHead = index(nextHead + 1);
        T item = items[nextHead];
        items[nextHead] = null;

        return item;
    }

    /**
     * Gets the element at the given index, where 0 is the front, 1 is the next item, and so forth.
     * @param index index of the element wanted.
     * @return element at the given index if existing, otherwise null.
     */
    public T get(int index) {
        if (outOfBound(index))
            return null;

        int i = index(nextHead + 1 + index);

        return items[i];
    }

    /**
     * Tests whether the given index is out of bound.
     * @param index index of an element.
     * @return true if the given index is out of bound, false otherwise.
     */
    private boolean outOfBound(int index) {
        return index < 0 || index >= size;
    }

    /**
     * Gets a valid index.
     * @param i the given index.
     * @return a valid index.
     */
    private int index(int i) {
        return mod(i, items.length);
    }

    /**
     * Performs the modulo operation result of which must be non-negative.
     * @param dividend the number to be divided, may be negative.
     * @param divider the number dividing, must be positive.
     * @return result (non-negative) of modulo operation.
     * @throws IllegalArgumentException if divider is zero or negative.
     */
    private static int mod(int dividend, int divider) {
        if (divider <= 0)
            throw new IllegalArgumentException("argument divider must be positive");

        int ret = dividend % divider;

        return ret >= 0 ? ret : ret + divider;
    }

    /**
     * Tests whether there is a need to resize <code>items</code>.
     * @return true if <code>items</code> needs to be resized, false otherwise.
     */
    private boolean needResize() {
        return size == capacity ||
                (capacity >= RESIZE_THRESHOLD && size <= capacity * USAGE_RATIO_THRESHOLD);
    }

    /**
     * Calculates the length of the resized <code>items</code>.
     * @return the length of the new <code>items</code>.
     */
    private int newCapacity() {
        if (size == capacity)
            return capacity * RESIZE_FACTOR;
        else
            return items.length / RESIZE_FACTOR;
    }

    /**
     * Checks if need resizing. If true, resizes <code>items</code>.
     */
    private void checkResize() {
        if (needResize())
            resize();
    }

    /**
     * Resize the <code>items</code>.
     */
    private void resize() {
        capacity = newCapacity();
        T[] newItems = (T[]) new Object[capacity + 1];
        int headIndex = index(nextHead + 1);
        int tailIndex = index(nextTail - 1);

        if (nextTail > nextHead)
            System.arraycopy(newItems, 0, items, headIndex, size);
        else {
            int lenHeadToEnd = items.length - 1 -nextHead;
            int lenStartToTail = nextTail;
            System.arraycopy(newItems, 0, items, headIndex, lenHeadToEnd);
            System.arraycopy(newItems, lenHeadToEnd, items, tailIndex, lenStartToTail);
        }

        items = newItems;
        nextHead = items.length - 1;
        nextTail = size;
    }
}
