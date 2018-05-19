import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class LeapYearTest {
    @Test
    public void testIsLeapYear() {
        assertTrue(LeapYear.isLeapYear(2000));
        assertTrue(LeapYear.isLeapYear(2004));
        assertTrue(LeapYear.isLeapYear(2008));
        assertTrue(LeapYear.isLeapYear(2012));
        assertTrue(LeapYear.isLeapYear(2016));

        assertFalse(LeapYear.isLeapYear(2001));
        assertFalse(LeapYear.isLeapYear(2002));
        assertFalse(LeapYear.isLeapYear(3000));
    }
}
