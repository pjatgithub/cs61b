import static org.junit.Assert.*;
import org.junit.Test;

public class ArithmeticTest {

    /** Performs a few arbitrary tests to see if the product method is correct */

    @Test 
    public void testProduct() {
        /* assertEquals for comparison of ints takes two arguments:
        assertEquals(expected, actual).
        if it is false, then the assertion will be false, and this test will fail.
        */

        assertEquals(30, Arithmetic.product(5, 6));
        assertEquals(-30, Arithmetic.product(5, -6));
        assertEquals(0, Arithmetic.product(0, -6));
    }

    /** Performs a few arbitrary tests to see if the sum method is correct */

    @Test 
    public void testSum() {

        assertEquals(11, Arithmetic.sum(5, 6));
        assertEquals(-1, Arithmetic.sum(5, -6));
        assertEquals(-6, Arithmetic.sum(0, -6));
        assertEquals(0, Arithmetic.sum(6, -6));
    }

    /**
     * Performs a few tests about <code>reverse</code> method.
     */
    @Test(timeout = 1000)
    public void testReverse() {
        IntList list = IntList.of(1,2,3,4,5);
        IntList reversed = IntList.reverse(list);

        assertEquals(reversed, IntList.of(5,4,3,2,1));
        assertNotEquals(list, IntList.of(1,2,3,4,5));
        IntList.reverse(null);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {        
        jh61b.junit.TestRunner.runTests("all", ArithmeticTest.class);
    }
}
