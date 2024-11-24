package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
        /* Parent of this Node. */
        private Node parent;
        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Set<K> keys = new HashSet<>();
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
        if(p == null) {
            return null;
        }
        if(key.compareTo(p.key) == 0) {
            return p.value;
        }else if(key.compareTo(p.key) < 0 ){
            return getHelper(key,p.left);
        }else {
            return getHelper(key,p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if(root == null) {
            return null;
        }
        if(key.compareTo(root.key) == 0) {
            return root.value;
        }else if (key.compareTo(root.key) < 0 ) {
            return getHelper(key,root.left);
        }else {
            return getHelper(key,root.right);
        }
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private BSTMap<K,V> putHelper(K key, V value, Node p) {
        BSTMap<K,V> subMap = new BSTMap<>();
        subMap.root = p;
        if(p == null) {
            Node newRoot = new Node(key,value);
            subMap.root = newRoot;
            newRoot.value = value;
            return subMap;
        }
        if(key.compareTo(p.key) == 0){
            p.value = value;
        }else if(key.compareTo(p.key) < 0) {
            BSTMap<K,V> leftMap = putHelper(key,value,p.left);
            subMap.root.left = leftMap.root;
            leftMap.root.parent = subMap.root;
        }else {
            BSTMap<K,V> rightMap = putHelper(key,value,p.right);
            subMap.root.right = rightMap.root;
            rightMap.root.parent = subMap.root;
        }
        return subMap;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if(root == null){
            root = new Node(key,value);
            size += 1;
            keys.add(key);
        }else if (key.compareTo(root.key) < 0) {
            BSTMap<K,V> subMap = putHelper(key,value,root.left);
            root.left = subMap.root;
            subMap.root.parent = root;
            size += 1;
            keys.add(key);
        }else if(key.compareTo(root.key) > 0){
            BSTMap<K,V> subMap = putHelper(key,value,root.right);
            root.right = subMap.root;
            subMap.root.parent = root;
            size += 1;
            keys.add(key);
        }else {
            root.value = value;
        }
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
        return keys;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        Node keyNode = findNode(key,root);
        if(keyNode == null) {
            return null;
        }
        V keyValue = keyNode.value;
        if(keyNode.left == null && keyNode.right == null) { // no child
            removeLeaf(keyNode);
        }else if(keyNode.left != null && keyNode.right != null) { // two children
            removeRoot(keyNode);
        }else { // one child
            removeBranch(keyNode);
        }
        keys.remove(key);
        size -= 1;
        return keyValue;
    }
    /**
     * remove a leaf
     */
    private void removeLeaf(Node keyNode){
        if(keyNode.parent == null ) { // no child and no parent (root)
            root = null;
        }else {
            if(keyNode.key.compareTo(keyNode.parent.key) < 0 ){
                keyNode.parent.left = null;
            }else {
                keyNode.parent.right = null;
            }
        }
    }
    /**
     * remove a branch
     */
    private void removeBranch(Node keyNode) {
        Node childNode = (keyNode.left != null) ? keyNode.left : keyNode.right;
        Node parentNode = keyNode.parent;
        if( parentNode == null ) { // one child and no parent
            root = childNode;
            childNode.parent = null;
        }else { // one child and one parent;
            if(keyNode.key.compareTo(keyNode.parent.key) < 0){ // keyNode is the left child of keyNode.parent
                parentNode.left = childNode;
                childNode.parent = parentNode;
            }
        }
    }
    /**
     * remove a root
     */
    private void removeRoot(Node keyNode) {
        Node biggestNode = findBiggestNode(keyNode.left);
        swap(biggestNode,keyNode);
        if(biggestNode.parent == keyNode ) {
            keyNode.left = biggestNode.left;
            if(biggestNode.left != null){
                biggestNode.left.parent = keyNode;
            }
        }else {
            biggestNode.parent.right = null;
        }
    }
    /**
     * swap the key and the value of two nodes
     */
    private void swap(Node node1,Node node2 ) {
        K tempKey = node1.key;
        V tempValue = node1.value;
        node1.key = node2.key;
        node1.value = node2.value;
        node2.key = tempKey;
        node2.value = tempValue;
    }
    /**
     * find the biggest node in the left tree
     */
    private Node findBiggestNode(Node root) {
        Node biggestNode= root;
        while(biggestNode.right != null) {
            biggestNode = biggestNode.right;
        }
        return biggestNode;
    }
    /**
     * find the corresponding node
     */
    private Node findNode(K key,Node root) {
        Node keyNode = root;
        if(root == null ) {
            return null;
        }
        if(key.compareTo(keyNode.key) == 0 ) {
            return keyNode;
        }else if(key.compareTo(root.key) < 0 ) {
            return findNode(key,keyNode.left);
        }else {
            return findNode(key,keyNode.right);
        }
    }
    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        Node keyNode = findNode(key,root);
        if(keyNode == null){
            return null;
        }
        if(keyNode.value == value){
            return remove(key);
        }else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keys.iterator();
    }
    public static void main(String[] args) {
        BSTMap<Integer,String> map = new BSTMap<>();
        BSTMap<Integer,String> map1 = new BSTMap<>();
        map.put(10,"hello");
        map.put(0,"world");
        map.put(30,"this");
        map.put(-3,"is");
        map.put(5,"hewei");
        map1.put(1,"hello");
        Set<Integer> keys = map.keySet();
        Iterator<Integer> interator = map.iterator();
    }
}
