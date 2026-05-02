package org.example;
import java.util.Random;
public class MyHashTable<K, V> {
    private HashNode<K, V>[] chainArray;
    private int M = 11;
    private int size;
    private static class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;
        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    public MyHashTable() {
        this(11);
    }
    @SuppressWarnings("unchecked")
    public MyHashTable(int M) {
        this.M = M;
        chainArray = (HashNode<K, V>[]) new HashNode[M];
        size = 0;
    }
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = chainArray[index];
        chainArray[index] = newNode;
        size++;
    }
    public V get(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        HashNode<K, V> prev = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    chainArray[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }
    public boolean contains(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                if (current.value.equals(value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }
    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                if (current.value.equals(value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }
    public int[] getBucketSizes() {
        int[] sizes = new int[M];
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                sizes[i]++;
                current = current.next;
            }
        }
        return sizes;
    }
}
class MyTestingClass {
    private int id;
    private String name;
    public MyTestingClass(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Override
    public int hashCode() {
        return 31 * id + (name != null ? name.hashCode() : 0);
    }
}
class Student {
    private String name;
    public Student(String name) {
        this.name = name;
    }
}
class TestHashTable {
    public static void main(String[] args) {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>();
        Random rand = new Random();
        for (int i = 0; i < 10000; i++) {
            table.put(new MyTestingClass(rand.nextInt(10000), "Name" + i), new Student("Student" + i));
        }
        int[] sizes = table.getBucketSizes();
        for (int i = 0; i < sizes.length; i++) {
            System.out.println("Bucket " + i + ": " + sizes[i]);
        }
    }
}