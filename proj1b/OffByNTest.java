import org.junit.Test;
import org.junit.Assert;

/**
 * <code>OffByNTest</code> is a class for testing {@link OffByN}.
 *
 * @author Hongbin Jin
 */
public class OffByNTest {
    /**
     * Tests {@link OffByN#equalChars(char, char)}
     */
    @Test
    public void testEqualChars() {
        CharacterComparator equal = new OffByN(0);
        CharacterComparator offByOne = new OffByN(1);
        CharacterComparator offByThree = new OffByN(3);

        Assert.assertTrue(equal.equalChars('a', 'a'));
        Assert.assertTrue(offByOne.equalChars('a', 'b'));
        Assert.assertTrue(offByOne.equalChars('b', 'a'));
        Assert.assertTrue(offByThree.equalChars('a', 'd'));
        Assert.assertTrue(offByThree.equalChars('d', 'a'));

        Assert.assertFalse(equal.equalChars('a', 'b'));
        Assert.assertFalse(offByOne.equalChars('a', 'a'));
        Assert.assertFalse(offByThree.equalChars('a', 'a'));
    }
}
