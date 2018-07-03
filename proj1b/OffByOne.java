/**
 * <code>OffByOne</code> is a character comparator and tests if two characters are
 * different exactly by one.
 *
 * @author Hongbin Jin
 */
public class OffByOne implements CharacterComparator {
    /**
     * Returns true if two characters are different exactly by one.
     *
     * @param x one character to be compared.
     * @param y another character to be compared.
     * @return true if different by one, false otherwise.
     */
    @Override
    public boolean equalChars(char x, char y) {
        return x + 1 == y || y + 1 == x;
    }
}
