//Author: Peter Bergen
import java.util.ArrayList;

public class MyHashMap<K, V> {

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K k, V v, Entry<K, V> n) {
            key = k;
            value = v;
            next = n;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    private static final double LOAD_FACTOR = 0.75;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        capacity = 1024;
        table = (Entry<K, V>[]) new Entry[capacity];
        size = 0;
    }

    public int size() {
        return size;
    }

    private int indexFor(Object key) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        if (h < 0) h = -h;
        return h % capacity;
    }

    public V get(K key) {
        int idx = indexFor(key);
        Entry<K, V> e = table[idx];
        while (e != null) {
            if (e.key.equals(key)) {
                return e.value;
            }
            e = e.next;
        }
        return null;
    }

    public void put(K key, V value) {
        if ((size + 1.0) / capacity > LOAD_FACTOR) {
            resize();
        }
        int idx = indexFor(key);
        Entry<K, V> e = table[idx];
        while (e != null) {
            if (e.key.equals(key)) {
                e.value = value;
                return;
            }
            e = e.next;
        }
        Entry<K, V> newEntry = new Entry<K, V>(key, value, table[idx]);
        table[idx] = newEntry;
        size++;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int oldCapacity = capacity;
        capacity = capacity * 2;
        Entry<K, V>[] oldTable = table;
        table = (Entry<K, V>[]) new Entry[capacity];
        size = 0;

        for (int i = 0; i < oldCapacity; i++) {
            Entry<K, V> e = oldTable[i];
            while (e != null) {
                put(e.key, e.value);
                e = e.next;
            }
        }
    }

    public Iterable<V> values() {
        ArrayList<V> list = new ArrayList<V>(size);
        for (int i = 0; i < capacity; i++) {
            Entry<K, V> e = table[i];
            while (e != null) {
                list.add(e.value);
                e = e.next;
            }
        }
        return list;
    }
}
