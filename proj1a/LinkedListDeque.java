import java.util.Iterator;

/**
 * <code>LinkedListDequeue</code> is an implementation of double-ended queues.
 *
 * @author Hongbin Jin
 * @param <T> type of the elements to be hold
 */
public class LinkedListDeque<T> {
    private final Node<T> sentinel;
    private int size;

    /**
     * Initializes an empty doubly linked list.
     */
    public LinkedListDeque() {
        sentinel = new Node<>();
        sentinel.next = sentinel;
        sentinel.previous = sentinel;
        size = 0;
    }

    /**
     * Adds an element at the head of the deque.
     * @param item an element to be added.
     */
    public void addFirst(T item) {
        size++;
        Node<T> newHead = new Node<>();
        newHead.item = item;
        Node<T> oldHead = sentinel.next;

        sentinel.next = newHead;
        newHead.previous = sentinel;
        newHead.next = oldHead;
        oldHead.previous = newHead;
    }

    /**
     * Adds an element at the tail of the deque.
     * @param item an element to be added.
     */
    public void addLast(T item) {
        size++;
        Node<T> newLast = new Node<>();
        newLast.item = item;
        Node<T> oldLast = sentinel.previous;

        oldLast.next = newLast;
        newLast.previous = oldLast;
        newLast.next = sentinel;
        sentinel.previous = newLast;
    }

    /**
     * Tests whether the deque is empty.
     * @return true if empty, otherwise false.
     */
    public boolean isEmpty() {
        return size == 0;
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
        Node<T> previous = sentinel;

        for (int i = 0; i < size; i++) {
            System.out.printf("%s%s", previous.next.item.toString(), i == size - 1 ? "" : " ");
            previous = previous.next;
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

        size--;
        Node<T> oldHead = sentinel.next;
        Node<T> newHead = oldHead.next;

        sentinel.next = newHead;
        newHead.previous = sentinel;

        T item = oldHead.item;
        oldHead.item = null;

        return item;
    }

    /**
     * Removes and returns the element at the tail of the deque.
     * @return the removed last element if existing, otherwise null.
     */
    public T removeLast() {
        if (isEmpty())
            return null;

        size--;
        Node<T> oldLast = sentinel.previous;
        Node<T> newLast = oldLast.previous;

        newLast.next = sentinel;
        sentinel.previous = newLast;

        T item = oldLast.item;
        oldLast.item = null;

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

        Node<T> previous = sentinel;

        for (int i = 0; i < size; i++)
            previous = previous.next;

        return previous.item;
    }

    /**
     * Gets the element at the given index recursively.
     * @param index index of an element.
     * @return element at the given index if existing, otherwise null.
     */
    public T getRecursive(int index) {
        if (outOfBound(index))
            return null;

        return getItemRecursive(sentinel.next, index);
    }

    /**
     * Gets the element at the given index recursively.
     * @param head head of a deque.
     * @param index index of an element.
     * @param <K> type of elements.
     * @return element at the given index.
     */
    private static <K> K getItemRecursive(Node<K> head, int index) {
        if (index == 0)
            return head.item;
        else
            return getItemRecursive(head.next, index - 1);
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
     * The underlying node holding elements of the deque.
     * @param <T> type of the elements to be hold
     */
    private static class Node<T> {
        T item;
        Node<T> previous;
        Node<T> next;
    }
}
