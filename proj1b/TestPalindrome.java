import org.junit.Test;
import static org.junit.Assert.*;

/**
 * <code>TestPalindrome</code> is a class for testing {@link Palindrome}.
 */
public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    /**
     * Tests {@link Palindrome#wordToDeque(String)}
     */
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    /**
     * Tests {@link Palindrome#isPalindrome(String)}
     */
    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("aaaaab"));
        assertFalse(palindrome.isPalindrome("Racecar"));
        assertFalse(palindrome.isPalindrome("nooN"));
    }

    /**
     * Tests {@link Palindrome#isPalindrome(String, CharacterComparator)}.
     */
    @Test
    public void testIsPalindromeWithCC() {
        CharacterComparator cc = new OffByOne();

        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("a", cc));
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertFalse(palindrome.isPalindrome("noon", cc));
    }
}
