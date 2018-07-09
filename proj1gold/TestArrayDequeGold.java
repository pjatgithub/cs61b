import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * Constructs an autograder for {@link StudentArrayDeque}.
 */
public class TestArrayDequeGold {
    /**
     * Tests {@link StudentArrayDeque#addFirst(Object)},
     *       {@link StudentArrayDeque#addLast(Object)},
     *       {@link StudentArrayDeque#removeFirst()},
     *       {@link StudentArrayDeque#removeLast()}.
     */
    @Test
    public void testArrayDeque() {
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
        final int max = 1000;
        int number;
        Integer expected, actual;
        StringBuilder stringBuilder = new StringBuilder();

        number = StdRandom.uniform(max);
        stringBuilder.append(String.format("addFirst(%d)\n", number));
        arrayDequeSolution.addFirst(number);
        studentArrayDeque.addFirst(number);

        number = StdRandom.uniform(max);
        stringBuilder.append(String.format("addFirst(%d)\n", number));
        arrayDequeSolution.addFirst(number);
        studentArrayDeque.addFirst(number);

        number = StdRandom.uniform(max);
        stringBuilder.append(String.format("addLast(%d)\n", number));
        arrayDequeSolution.addLast(number);
        studentArrayDeque.addLast(number);

        number = StdRandom.uniform(max);
        stringBuilder.append(String.format("addLast(%d)\n", number));
        arrayDequeSolution.addLast(number);
        studentArrayDeque.addLast(number);

        number = StdRandom.uniform(max);
        stringBuilder.append(String.format("addLast(%d)\n", number));
        arrayDequeSolution.addLast(number);
        studentArrayDeque.addLast(number);

        //Test removeFirst
        expected = arrayDequeSolution.removeFirst();
        actual = studentArrayDeque.removeFirst();
        stringBuilder.append("removeFirst()\n");
        assertEquals(stringBuilder.toString(), expected, actual);

        //Test removeLast
        expected = arrayDequeSolution.removeLast();
        actual = studentArrayDeque.removeLast();
        stringBuilder.append("removeLast()\n");
        assertEquals(stringBuilder.toString(), expected, actual);

        //Test removeLast
        expected = arrayDequeSolution.removeLast();
        actual = studentArrayDeque.removeLast();
        stringBuilder.append("removeLast()\n");
        assertEquals(stringBuilder.toString(), expected, actual);

        //Test removeFirst
        expected = arrayDequeSolution.removeFirst();
        actual = studentArrayDeque.removeFirst();
        stringBuilder.append("removeFirst()\n");
        assertEquals(stringBuilder.toString(), expected, actual);

        //Test removeLast
        expected = arrayDequeSolution.removeLast();
        actual = studentArrayDeque.removeLast();
        stringBuilder.append("removeLast()\n");
        assertEquals(stringBuilder.toString(), expected, actual);
    }
}
