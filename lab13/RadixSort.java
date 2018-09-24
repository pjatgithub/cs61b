import java.util.Arrays;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // Sort with LSD
        // Get maximum length
        int maxLength = Integer.MIN_VALUE;
        for (String ascii : asciis) {
            maxLength = Math.max(maxLength, ascii.length());
        }
        // Sort
        String[] result = Arrays.copyOf(asciis, asciis.length, String[].class);
        for (int index = maxLength - 1; index >= 0; index--) {
            sortHelperLSD(result, index);
        }

//        // Sort with MSD
//        String[] result = Arrays.copyOf(asciis, asciis.length, String[].class);
//        sortHelperMSD(result,0, result.length, 0);

        return result;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        final int radix = 256;
        int[] count = new int[radix + 2];
        // Counting
        for (String ascii : asciis) {
            count[index(ascii, index) + 2]++;
        }
        // Calculate starting position
        for (int i = 0; i <= radix; i++) {
            count[i + 1] += count[i];
        }
        // Distribute
        String[] copy = new String[asciis.length];
        for (String ascii : asciis) {
            copy[count[index(ascii, index) + 1]++] = ascii;
        }
        // Copy back
        System.arraycopy(copy, 0, asciis, 0, asciis.length);
    }

    /**
     * Gets the index of character at the given position
     * @param s a string
     * @param i position
     * @return index of chatacter, -1 if no such character
     */
    private static int index(String s, int i) {
        if (i >= s.length()) {
            return -1;
        } else {
            return s.charAt(i);
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        if (start + 1 >= end) {
            return;
        }
        // Counting
        final int radix = 256;
        int[] count = new int[radix + 2];
        for (int i = start; i < end; i++) {
            String ascii = asciis[i];
            count[index(ascii, index) + 2]++;
        }
        // Calculate starting position
        count[0] = start;
        for (int i = 0; i <= radix; i++) {
            count[i + 1] += count[i];
        }
        int[] starting = Arrays.copyOf(count, count.length);
        // Distribute
        String[] copy = new String[end - start];
        for (int i = start; i < end; i++) {
            String ascii = asciis[i];
            int j = index(ascii, index) + 1;
            copy[count[j] - start] = ascii;
            count[j]++;
        }
        // Copy back
        if (copy.length >= 0)
            System.arraycopy(copy, 0, asciis, 0 + start, copy.length);
        // Sort each bucket
        for (int i = 1; i <= radix; i++) {
            sortHelperMSD(asciis, starting[i], starting[i + 1], index + 1);
        }
    }
}
