package com.jc.datastructures.hashing;

/**
 * 使用探测方法实现的hashtable
 * <p>
 * 探测方法采用平方探测法  f(i)=i*i;
 */
public class QuadraticProbingHashTable<AnyType> {
    private static final int DEFAULT_TABLE_SIZE = 11;// 素数
    private int occupied;
    private HashEntry[] array;

    private static class HashEntry<AnyType> {
        public AnyType element;
        public boolean isActive;

        public HashEntry(AnyType element) {
            this(element, true);
        }

        public HashEntry(AnyType element, boolean isActive) {
            this.element = element;
            this.isActive = isActive;
        }
    }

    public QuadraticProbingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public QuadraticProbingHashTable(int size) {
        allocateArray(size);
        makeEmpty();
    }

    public void makeEmpty() {
        occupied = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    private void allocateArray(int size) {
        array = new HashEntry[nextPrime(size)];
    }

    private static int nextPrime(int size) {
        if (size % 2 == 0) {
            size++;
        }
        for (; !isPrime(size); size += 2) {

        }
        return size;
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3) {
            return true;
        }
        if (n == 1 || n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(AnyType x) {
        int pos = findPos(x);
        return isActive(pos);
    }


    private int findPos(AnyType x) {
        int offset = 1;
        int currentPos = myHash(x);
        while (array[currentPos] != null && !array[currentPos].element.equals(x)) {
            currentPos += offset;
            offset += 2;
            if (currentPos >= array.length) {
                currentPos -= array.length;
            }
        }
        return currentPos;
    }

    private boolean isActive(int pos) {
        return array[pos] != null && array[pos].isActive;
    }

    private int myHash(AnyType x) {
        int hashValue = x.hashCode();
        hashValue %= array.length;
        if (hashValue < 0) {
            hashValue += array.length;
        }
        return hashValue;
    }


    public boolean insert(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            return false;
        }
        if (array[currentPos] == null) {
            ++occupied;
        }
        array[currentPos] = new HashEntry(x, true);
        if (occupied > array.length / 2) {
            rehash();
        }

        return true;
    }


    public void remove(AnyType x) {
        int currentPos = findPos(x);
        if (isActive(currentPos)) {
            array[currentPos].isActive = false;
        }
    }


    private void rehash() {

        HashEntry<AnyType>[] oldArray = array;
        allocateArray(2 * oldArray.length);
        occupied = 0;

        for (int i = 0; i < oldArray.length; i++) {
            if (oldArray[i] != null && oldArray[i].isActive) {
                insert(oldArray[i].element);
            }
        }
    }
}
