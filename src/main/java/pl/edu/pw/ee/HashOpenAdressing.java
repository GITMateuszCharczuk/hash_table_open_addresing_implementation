package pl.edu.pw.ee;

import pl.edu.pw.ee.services.HashTable;

public abstract class HashOpenAdressing<T extends Comparable<T>> implements HashTable<T> {//duplikaty

    private final T nil = null;
    private final Comparable<T> Delete = (T) "ówér人繞босаḍḍ";
    private int size;
    private int nElems;
    private T[] hashElems;
    private final double correctLoadFactor;

    HashOpenAdressing() {
        this(2039); // initial size as random prime number
    }

    HashOpenAdressing(int size) {
        validateHashInitSize(size);

        this.size = size;
        this.hashElems = (T[]) new Comparable[this.size];
        this.correctLoadFactor = 0.75;
    }

    @Override
    public void put(T newElem) {
        validateInputElem(newElem);

        int key = newElem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil && !newElem.equals(hashElems[hashId]) && !hashElems[hashId].equals(Delete)) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
        if (!newElem.equals(hashElems[hashId])) {
            nElems++;
        }
        hashElems[hashId] = newElem;
        resizeIfNeeded();
    }

    @Override
    public T get(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil && !elem.equals(hashElems[hashId])) {
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
        return elem.equals(hashElems[hashId]) ? hashElems[hashId] : null;
    }

    @Override
    public void delete(T elem) {
        validateInputElem(elem);

        int key = elem.hashCode();
        int i = 0;
        int hashId = hashFunc(key, i);

        while (hashElems[hashId] != nil) {
            if (elem.equals(hashElems[hashId])) {
                hashElems[hashId] = (T) Delete;
                nElems--;
                break;
            }
            i = (i + 1) % size;
            hashId = hashFunc(key, i);
        }
    }

    private void validateHashInitSize(int initialSize) {
        if (initialSize < 1) {
            throw new IllegalArgumentException("Initial size of hash table cannot be lower than 1!");
        } else if (this instanceof HashQuadraticProbing && initialSize == 3) {
            throw new IllegalArgumentException("Hash size can't be 3 as HashQuadraticProbing dont support it!");
        }
    }

    private void validateInputElem(T newElem) {
        if (newElem == null) {
            throw new IllegalArgumentException("Input elem cannot be null!");
        } else if (newElem.equals(Delete)) {
            throw new IllegalArgumentException("Input elem cannot be ówér人繞босаḍḍ!");
        }

    }

    abstract int hashFunc(int key, int i);

    int getSize() {
        return size;
    }

    private void resizeIfNeeded() {
        double loadFactor = countLoadFactor();

        if (loadFactor >= correctLoadFactor) {
            doubleResize();
        }
    }

    private double countLoadFactor() {
        return (double) nElems / size;
    }

    private void doubleResize() {
        T[] newTab = (T[]) new Comparable[this.size];
        int nElemsCpy = nElems;
        System.arraycopy(hashElems, 0, newTab, 0, this.size);
        this.size *= 2;
        this.hashElems = (T[]) new Comparable[this.size];

        for (int i = 0; i < newTab.length; i++) {
            if (newTab[i] != null && newTab[i] != Delete) {
                put(newTab[i]);
            }
        }
        nElems = nElemsCpy;
    }

}
