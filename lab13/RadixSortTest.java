import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RadixSortTest {
    @Test
    public void testSort(){
        String[] unsorted = new String[] {
                "holiday", "today", "habit", "string",
                "class", "fly", "ancestor", "zoom", "exit",
                "carry", "dairy", "eastern", "nutrition",
                ""
        };
        String[] expected = Arrays.copyOf(unsorted,
                unsorted.length, String[].class);
        Arrays.sort(expected);
        String[] sorted = RadixSort.sort(unsorted);
        // Check
        Assert.assertArrayEquals(expected, sorted);
    }

    public static void main(String[] args) {
        RadixSortTest test = new RadixSortTest();
        test.testSort();
    }
}
