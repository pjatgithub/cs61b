package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;
    private static final int FACTOR = 2;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private double loadFactor() {
        return ((double) size) / buckets.length;
    }

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);

        return buckets[index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        checkNull(key, "key cannot be null");
        checkNull(value, "value cannot be null");

        if (needResize()) {
            resize();
        }

        ArrayMap<K, V> bucket = buckets[hash(key)];
        int sizeBefore, sizeAfter;

        sizeBefore = bucket.size();
        buckets[hash(key)].put(key, value);
        sizeAfter = bucket.size();
        size += sizeAfter - sizeBefore;
    }

    /**
     * Checks if whether {@code o} is {@code null}.
     * @param o object to be checked.
     * @param message message for exception.
     *
     * @throws IllegalArgumentException if {@code o} is {@code null}.
     */
    private void checkNull(Object o, String message) {
        if (o == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks whether resizing is needed.
     * @return true if resizing is needed, false otherwise.
     */
    private boolean needResize() {
        return loadFactor() >= MAX_LF;
    }

    /**
     * Gets number of buckets after resizing.
     * @return number of buckets.
     */
    private int newCapaity() {
        return 2 * buckets.length;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int capacity = newCapaity();
        ArrayMap<K, V>[] newBuckets = new ArrayMap[capacity];

        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = new ArrayMap<>();
        }

        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket) {
                V value = bucket.get(key);
                newBuckets[hash(key)].put(key, value);
            }
        }

        buckets = newBuckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>(size());

        for (ArrayMap<K, V> bucket : buckets) {
            for (K key : bucket) {
                keys.add(key);
            }
        }

        return keys;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        checkNull(key, "key cannot be null");

        int sizeBefore, sizeAfter;
        V value;
        ArrayMap<K, V> bucket = buckets[hash(key)];

        sizeBefore = bucket.size();
        value = bucket.remove(key);
        sizeAfter = bucket.size();
        size += sizeAfter - sizeBefore;

        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        checkNull(key, "key cannot be null");
        checkNull(value, "value cannot be null");

        int sizeBefore, sizeAfter;
        V removedValue;
        ArrayMap<K, V> bucket = buckets[hash(key)];

        sizeBefore = bucket.size();
        removedValue = bucket.remove(key, value);
        sizeAfter = bucket.size();
        size += sizeAfter - sizeBefore;

        return removedValue;
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIterator();
    }

    private class MyHashMapIterator implements Iterator<K> {
        int index = -1;
        int count = 0;
        int keySize = size();
        Iterator<K> it = null;

        @Override
        public boolean hasNext() {
            return count < keySize;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (it == null || !it.hasNext()) {
                while (buckets[++index].size() == 0) {
                    continue;
                }

                it = buckets[index].iterator();
            }

            count++;
            return it.next();
        }
    }
}
