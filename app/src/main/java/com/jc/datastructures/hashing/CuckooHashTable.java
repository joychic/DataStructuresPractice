package com.jc.datastructures.hashing;

import java.util.Random;

/**
 * 布谷鸟散列
 *
 * @param <AnyType>
 */
public class CuckooHashTable<AnyType> {
    private static final double MAX_LOAD = 0.4;
    private static final int ALLOWED_REHASHES = 1;
    private static final int DEFAULT_TABLE_SIZE = 101;

    private final HashFamily<? super AnyType> hashFunctions;
    private final int numHashFunctions;
    private AnyType[] array;
    private int currentSize;


    public interface HashFamily<AnyType> {
        int hash(AnyType x, int which);

        int getNumberOfFunctions();

        void generateNewFunctions();
    }


    public CuckooHashTable(HashFamily<? super AnyType> hashFunctions) {
        this(hashFunctions, DEFAULT_TABLE_SIZE);
    }

    public CuckooHashTable(HashFamily<? super AnyType> hashFunctions, int size) {
        allocateArray(newtPrime(size));
        doClear();
        this.hashFunctions = hashFunctions;
        numHashFunctions = hashFunctions.getNumberOfFunctions();
    }

    private void doClear() {
        currentSize = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
    }

    public int size() {
        return currentSize;
    }

    public int capacity() {
        return array.length;
    }

    public void makeEmpty() {
        doClear();
    }

    public boolean contains(AnyType x) {
        return findPos(x) != -1;
    }

    private int myhash(AnyType x, int which) {
        int hashVal = hashFunctions.hash(x, which);
        hashVal %= array.length;
        if (hashVal < 0) {
            hashVal += array.length;
        }

        return hashVal;
    }

    private int findPos(AnyType x) {
        for (int i = 0; i < numHashFunctions; i++) {
            int pos = myhash(x, i);
            if (array[pos] != null && array[pos].equals(x)) {
                return pos;
            }
        }
        return -1;
    }

    public boolean remove(AnyType x) {
        int pos = findPos(x);
        if (pos != -1) {
            array[pos] = null;
            currentSize--;
        }
        return pos != -1;
    }

    public boolean insert(AnyType x) {
        if (contains(x)) {
            return false;
        }

        if (currentSize >= array.length * MAX_LOAD) {
            expand();
        }

        return insertHelper1(x);
    }

    private Random r = new Random();
    private int rehashes = 0;

    private boolean insertHelper1(AnyType x) {
        final int COUNT_LIMIT = 100;

        while (true) {
            int lastPos = -1;
            int pos;

            for (int count = 0; count < COUNT_LIMIT; count++) {
                for (int i = 0; i < numHashFunctions; i++) {
                    pos = myhash(x, i);

                    if (array[pos] == null) {
                        array[pos] = x;
                        currentSize++;
                        return true;
                    }
                }

                int i = 0;
                do {
                    pos = myhash(x, r.nextInt(numHashFunctions));
                } while (pos == lastPos && i++ < 5);

                AnyType tmp = array[lastPos = pos];
                array[pos] = x;
                x = tmp;
            }

            if (++rehashes > ALLOWED_REHASHES) {
                expand();
                rehashes = 0;
            } else {
                rehash();
            }
        }

    }


    private boolean insertHelper2(AnyType x) {
        final int COUNT_LIMIT = 100;

        while (true) {
            for (int count = 0; count < COUNT_LIMIT; count++) {
                int pos = myhash(x, count % numHashFunctions);

                AnyType tmp = array[pos];
                array[pos] = x;

                if (tmp == null) {
                    return true;
                } else {
                    x = tmp;
                }

            }

            if (++rehashes > ALLOWED_REHASHES) {
                expand();
                rehashes = 0;
            } else {
                rehash();
            }

        }
    }

    private void expand() {
        rehash((int) (array.length / MAX_LOAD));
    }

    private void rehash() {
        hashFunctions.generateNewFunctions();
        rehash(array.length);
    }


    private void rehash(int newLength) {
        AnyType[] oldArray = array;
        allocateArray(newtPrime(newLength));
        currentSize = 0;

        for (AnyType x : oldArray) {
            if (x != null) {
                insert(x);
            }
        }

    }

    private void allocateArray(int arraySize) {
        array = (AnyType[]) new Object[arraySize];
    }

    private int newtPrime(int size) {
        if (size / 2 == 0) {
            size++;
        }

        for (; isPrime(size); size += 2) {

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


        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


}
