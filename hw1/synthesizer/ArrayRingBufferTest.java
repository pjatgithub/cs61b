package synthesizer;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ArrayRingBufferTest {
    @Test
    public void testIsFull() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i < capacity; i++) {
            assertFalse(buffer.isFull());
            buffer.enqueue(i);
        }
        assertTrue(buffer.isFull());

        for (int i = 0; i < capacity; i++) {
            buffer.dequeue();
            assertFalse(buffer.isFull());
        }
    }

    @Test
    public void testIsEmpty() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);
        assertTrue(buffer.isEmpty());

        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(i);
            assertFalse(buffer.isEmpty());
        }

        for (int i = 0; i < capacity; i++) {
            assertFalse(buffer.isEmpty());
            buffer.dequeue();
        }
        assertTrue(buffer.isEmpty());
    }

    @Test
    public void testCapacity() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(i);
            assertEquals(capacity, buffer.capacity());
        }

        for (int i = 0; i < capacity; i++) {
            buffer.dequeue();
            assertEquals(capacity, buffer.capacity());
        }
    }

    @Test
    public void testFillCount() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i < capacity; i++) {
            assertEquals(i, buffer.fillCount());
            buffer.enqueue(i);
        }

        for (int i = capacity; i > 0; i--) {
            assertEquals(i, buffer.fillCount());
            buffer.dequeue();
        }
        assertEquals(0, buffer.fillCount());
    }

    @Test
    public void testIndex() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        assertEquals(5, buffer.index(5));
        assertEquals(0, buffer.index(9 + 1));
        assertEquals(9, buffer.index(0 - 1));
    }

    @Test(expected = RuntimeException.class)
    public void testDequeueEmpty() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        buffer.dequeue();
    }

    @Test(expected = RuntimeException.class)
    public void testEnqueueFull() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i <= capacity; i++) {
            buffer.enqueue(i);
        }
    }

    @Test
    public void testEnqueueAnDequeue() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(i);
        }

        for (int i = 0; i < capacity; i++) {
            assertEquals(i, (int) buffer.dequeue());
        }
    }

    @Test(expected = RuntimeException.class)
    public void testPeekEmpty() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        buffer.peek();
    }

    @Test
    public void testPeek() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(i);
        }

        for (int i = 0; i < capacity; i++) {
            assertEquals(i, (int) buffer.peek());
            buffer.dequeue();
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testIterator() {
        int capacity = 10;
        ArrayRingBuffer<Integer> buffer = new ArrayRingBuffer<>(capacity);

        for (int i = 0; i < capacity; i++) {
            buffer.enqueue(i);
        }

        Iterator<Integer> iterator = buffer.iterator();

        for (int i = 0; i < capacity; i++) {
            assertTrue(iterator.hasNext());
            assertEquals(i, (int) iterator.next());
        }

        assertFalse(iterator.hasNext());
        iterator.next();
    }
}
