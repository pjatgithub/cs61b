/**
 * <code>OffByN</code> is a character comparator and tests if two characters are
 * different by the given number.
 *
 * @author Hongbin Jin
 */
public class OffByN implements CharacterComparator {
    /**
     * The number by which two characters are different.
     */
    private int n;

    /**
     * Initializes a by-N character comparator.
     *
     * @param n the number by which two characters are different.
     */
    public OffByN(int n) {
        this.n = n;
    }

    /**
     * Returns true if two characters are different exactly by specified N.
     *
     * @param x one character to be compared.
     * @param y another character to be compared.
     * @return true if two character are different by N, false otherwise.
     */
    @Override
    public boolean equalChars(char x, char y) {
        return x + n == y || y + n == x;
    }
}
