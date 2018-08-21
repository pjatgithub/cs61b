package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }

        int compare = key.compareTo(p.key);

        if (compare == 0) {
            // key == p.key
            return p.value;
        } else if (compare < 0) {
            // key < p.key, search p's left child.
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        checkKey(key);

        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        }

        int compare = key.compareTo(p.key);

        if (compare == 0) {
            p.value = value;
        } else if (compare < 0) {
            p.left = putHelper(key, value, p.left);
        } else {
            p.right = putHelper(key, value, p.right);
        }

        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        checkKey(key);

        root = putHelper(key, value, root);
        size++;
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
        keySetHelper(root, keys);

        return keys;
    }

    /**
     * Generates a keys of all contained keys.
     * @param root root node of current BST.
     * @param keys keys in which all keys are put.
     */
    private void keySetHelper(Node root, Set<K> keys) {
        if (root == null) {
            return;
        }

        keySetHelper(root.left, keys);
        if (root.value != null) {
            keys.add(root.key);
        }
        keySetHelper(root.right, keys);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        V removed = get(key);

        if (removed != null) {
            root = removeHelper(root, key);
            size--;
        }

        return removed;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        V removed = get(key);

        if (!value.equals(removed)) {
            return null;
        }

        root = removeHelper(root, key);
        size--;

        return removed;
    }

    /**
     * Removes a node with the given key.
     * @param root root node of current BST.
     * @param key key to identify the node to be removed.
     * @return root node after deletion.
     */
    private Node removeHelper(Node root, K key) {
        int compare = key.compareTo(root.key);

        if (compare == 0) {
            Node newRoot = min(root.right);

            if (newRoot == null) {
                root = root.left;
            } else {
                newRoot.right = deleteMin(root.right);
                newRoot.left = root.left;
                root = newRoot;
            }
        } else if (compare < 0) {
            root.left = removeHelper(root.left, key);
        } else {
            root.right = removeHelper(root.right, key);
        }

        return root;
    }

    /**
     * Gets the node with the smallest key of root.
     * @param root root node of the current BST.
     * @return node with smallest key.
     */
    private Node min(Node root) {
        if (root == null) {
            return null;
        }

        if (root.left == null) {
            return root;
        } else {
            return min(root.left);
        }
    }

    /**
     * Deletes the node with the smallest key.
     * @param root root node of the current BST.
     * @return new root after deletion.
     */
    private Node deleteMin(Node root) {
        if (root == null) {
            return null;
        }

        if (root.left == null) {
            return root.right;
        } else {
            root.left = deleteMin(root.left);

            return root;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIterator();
    }

    private class BSTMapIterator implements Iterator<K> {
        Deque<Node> stack;
        Node current;

        BSTMapIterator() {
            stack = new ArrayDeque<>();
            current = root;
        }

        @Override
        public boolean hasNext() {
            return current != null || !stack.isEmpty();
        }

        @Override
        public K next() {
            K key = null;

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            while (current != null) {
                stack.offerFirst(current);
                current = current.left;
            }

            current = stack.pollFirst();
            key = current.key;
            current = current.right;

            return key;
        }
    }

    /**
     * Checks whether {@code key} is {@code null}.
     *
     * @param key key to be checked.
     *
     * @throws IllegalArgumentException if {@code key} is {@code null}.
     */
    private void checkKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cannot be null");
        }
    }
}
