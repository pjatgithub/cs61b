import org.junit.Assert;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        Assert.assertTrue(Flik.isSameNumber(1, 1));
        Assert.assertFalse(Flik.isSameNumber(1, 2));
        Assert.assertTrue(Flik.isSameNumber(127, 127));
        Assert.assertFalse(Flik.isSameNumber(128, 128));
        Assert.assertFalse(Flik.isSameNumber(1000, 1000));
    }
}