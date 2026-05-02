package org.example;
import java.util.*;
public class BST<K extends Comparable<K>, V> {
    private Node root;
    private int size;
    private class Node {
        private K key;
        private V val;
        private Node left, right;
        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }
    public static class Entry<K, V> {
        private K key;
        private V value;
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
    }
    public void put(K key, V val) {
        if (root == null) {
            root = new Node(key, val);
            size++;
            return;
        }
        Node current = root;
        Node parent = null;
        int cmp = 0;
        while (current != null) {
            parent = current;
            cmp = key.compareTo(current.key);
            if (cmp < 0)
                current = current.left;
            else if (cmp > 0)
                current = current.right;
            else {
                current.val = val;
                return;
            }
        }
        if (cmp < 0)
            parent.left = new Node(key, val);
        else
            parent.right = new Node(key, val);
        size++;
    }
    public V get(K key) {
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else
                return node.val;
        }
        return null;
    }
    public void delete(K key) {
        Node toDelete = root;
        Node parent = null;
        int cmp = 0;
        while (toDelete != null && (cmp = key.compareTo(toDelete.key)) != 0) {
            parent = toDelete;
            if (cmp < 0)
                toDelete = toDelete.left;
            else
                toDelete = toDelete.right;
        }
        if (toDelete == null)
            return;
        size--;
        if (toDelete.left == null && toDelete.right == null) {
            if (parent == null)
                root = null;
            else if (parent.left == toDelete)
                parent.left = null;
            else
                parent.right = null;
        }
        else if (toDelete.left == null || toDelete.right == null) {
            Node child = (toDelete.left != null) ? toDelete.left : toDelete.right;
            if (parent == null)
                root = child;
            else if (parent.left == toDelete)
                parent.left = child;
            else
                parent.right = child;
        }
        else {
            Node successor = toDelete.right;
            Node successorParent = toDelete;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            toDelete.key = successor.key;
            toDelete.val = successor.val;
            if (successorParent.left == successor)
                successorParent.left = successor.right;
            else
                successorParent.right = successor.right;
        }
    }
    public int size() {
        return size;
    }
    public Iterable<Entry<K, V>> iterator() {
        return () -> new Iterator<Entry<K, V>>() {
            private Stack<Node> stack = new Stack<>();
            private Node current = root;
            {
                pushLeft(current);
            }
            private void pushLeft(Node node) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }
            @Override
            public Entry<K, V> next() {
                Node node = stack.pop();
                pushLeft(node.right);
                return new Entry<>(node.key, node.val);
            }
        };
    }
    public static void main(String[] args) {
        BST<String, Integer> tree = new BST<>();
        tree.put("A", 1);
        tree.put("C", 3);
        tree.put("B", 2);
        for (var elem : tree.iterator()) {
            System.out.println("key is " + elem.getKey() + " and value is " + elem.getValue());
        }
    }
}
