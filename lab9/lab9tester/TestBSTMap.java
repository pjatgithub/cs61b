package lab9tester;

import static org.junit.Assert.*;

import lab9.Map61B;
import org.junit.Test;
import lab9.BSTMap;

import java.util.*;

/**
 * Tests by Brendan Hu, Spring 2015, revised for 2018 by Josh Hug
 */
public class TestBSTMap {

    @Test
    public void sanityGenericsTest() {
        try {
            BSTMap<String, String> a = new BSTMap<String, String>();
            BSTMap<String, Integer> b = new BSTMap<String, Integer>();
            BSTMap<Integer, String> c = new BSTMap<Integer, String>();
            BSTMap<Boolean, Integer> e = new BSTMap<Boolean, Integer>();
        } catch (Exception e) {
            fail();
        }
    }

    //assumes put/size/containsKey/get work
    @Test
    public void sanityClearTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1 + i);
            //make sure put is working via containsKey and get
            assertTrue(null != b.get("hi" + i));
            assertTrue(b.get("hi" + i).equals(1 + i));
            assertTrue(b.containsKey("hi" + i));
        }
        assertEquals(455, b.size());
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(null, b.get("starChild"));
        assertEquals(0, b.size());
        b.put("starChild", 5);
        assertTrue(((Integer) b.get("starChild")).equals(5));
        b.put("KISS", 5);
        assertTrue(((Integer) b.get("KISS")).equals(5));
        assertNotEquals(null, b.get("starChild"));
        assertEquals(2, b.size());
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(0, b.size());
        b.put("hi", 1);
        assertEquals(1, b.size());
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
        }
        assertEquals(456, b.size());
    }

    //assumes get/containskey work
    @Test
    public void sanityPutTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi"));
        assertTrue(b.get("hi") != null);
    }

    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests(TestBSTMap.class);
    }

    /**
     * Tests {@link BSTMap#keySet() BSTMap.keySet()}.
     */
    @Test
    public void testKeySet() {
        Set<String> expected = Set.of("1", "2", "11", "123", "345", "777");
        Map61B<String, Integer> map = new BSTMap<>();

        for (String num : expected) {
            map.put(num, Integer.valueOf(num));
        }
        assertEquals(expected.size(), map.size());

        assertEquals(expected, map.keySet());
    }

    @Test
    public void testRemove() {
        Set<String> original = Set.of("1", "2", "11", "123", "345", "777");
        Set<String> removed = Set.of("1", "2", "11");
        Set<String> left = Set.of("123", "345", "777");
        Map61B<String, Integer> map = new BSTMap<>();

        for (String num : original) {
            map.put(num, Integer.valueOf(num));
        }
        assertEquals(original.size(), map.size());

        for (String num : removed) {
            assertEquals(Integer.valueOf(num), map.remove(num));
        }
        assertEquals(left.size(), map.size());
        assertEquals(left, map.keySet());
    }

    @Test
    public void testRemoveWithValue() {
        Set<String> original = Set.of("1", "2", "11", "123", "345", "777");
        Set<String> removed = Set.of("1", "2", "11");
        Set<String> left = Set.of("123", "345", "777");
        Map61B<String, Integer> map = new BSTMap<>();

        for (String num : original) {
            map.put(num, Integer.valueOf(num));
        }
        assertEquals(original.size(), map.size());

        for (String num : removed) {
            Integer n = Integer.valueOf(num);
            assertEquals(n, map.remove(num, n));
        }
        assertEquals(left.size(), map.size());
        assertEquals(left, map.keySet());

        for (String num : left) {
            Integer n = Integer.valueOf(num);
            assertNull(map.remove(num, n + 1));
        }
        assertEquals(left.size(), map.size());
        assertEquals(left, map.keySet());

        for (String num : left) {
            Integer n = Integer.valueOf(num);
            assertEquals(n, map.remove(num, n));
        }
        assertEquals(0, map.size());
        assertEquals(0, map.keySet().size());
    }

    @Test
    public void testIterator() {
        Set<String> expected = Set.of("1", "2", "11", "123", "345", "777");
        Set<String> actual = new HashSet<>(expected.size());
        List<String> sortExpected = new ArrayList<String>(expected);
        Collections.sort(sortExpected);
        List<String> sortActual = new ArrayList<>(expected.size());
        Map61B<String, Integer> map = new BSTMap<>();

        for (String s : expected) {
            map.put(s, Integer.valueOf(s));
        }

        for (String s : map) {
            actual.add(s);
            sortActual.add(s);
        }
        assertEquals(expected, actual);
        assertEquals(sortExpected, sortActual);
    }
}
