/**
 * <code>Palindrome</code> is a class to determine whether a string is a palindrome.
 *
 * @author Hongbin Jin
 */
public class Palindrome {
    /**
     * Converts a string to a deque of characters from that string.
     *
     * @param word a string to be converted.
     * @return a deque of characters converted from the given string.
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
//        Deque<Character> deque = new ArrayDeque<>();

        for (int i = 0; i < word.length(); i++) {
            deque.addLast(word.charAt(i));
        }

        return deque;
    }

    /**
     * Test whether a string is a palindrome.
     *
     * @param word the tested string.
     * @return true if the string is a palindrome, false otherwise.
     */
    public boolean isPalindrome(String word) {
        return isPalindrome(word, new OffByN(0));
    }

    /**
     * Test whether a string is a palindrome according to a specific rule.
     * @param word the string to be tested.
     * @param cc comparison rule.
     * @return true if the string is a palindrome, false otherwise.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);

        while (deque.size() > 1) {
            char first = deque.removeFirst();
            char last = deque.removeLast();

            if (!cc.equalChars(first, last)) {
                return false;
            }
        }

        return true;
    }
}
