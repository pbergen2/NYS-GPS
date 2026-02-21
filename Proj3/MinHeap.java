//Author: Peter Bergen
@SuppressWarnings("unchecked")
public class MinHeap<T extends Comparable<T>> {

    private T[] heap;
    private int size;

    public MinHeap() {
        this(16);
    }

    public MinHeap(int capacity) {
        heap = (T[]) new Comparable[capacity + 1];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(T item) {
        if (size == heap.length - 1) {
            resize(heap.length * 2);
        }
        heap[++size] = item;
        swim(size);
    }

    public T extractMin() {
        if (size == 0) return null;
        T min = heap[1];
        heap[1] = heap[size];
        heap[size] = null;
        size--;
        sink(1);
        return min;
    }

    private void swim(int k) {
        while (k > 1 && greater(k / 2, k)) {
            swap(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && greater(j, j + 1)) j++;
            if (!greater(k, j)) break;
            swap(k, j);
            k = j;
        }
    }

    private boolean greater(int i, int j) {
        return heap[i].compareTo(heap[j]) > 0;
    }

    private void swap(int i, int j) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }

    private void resize(int newCap) {
        T[] newHeap = (T[]) new Comparable[newCap];
        for (int i = 1; i <= size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
}
