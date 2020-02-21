package com.jc.datastructures.hashing;

import java.util.LinkedList;
import java.util.List;

/**
 * 采用分离链接法实现的 hashtable
 */
public class SeparateChainingHashTable<AnyType> {
    private static final int DEFAULT_TABLE_SIZE = 101;
    private List<AnyType>[] theLists;
    private int currentSize;


    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int currentSize) {
        theLists = new LinkedList[newtPrime(currentSize)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<>();
        }
    }

    public void insert(AnyType x) {
        List<AnyType> which = theLists[myhash(x)];
        if (!which.contains(x)) {
            which.add(x);

            if (++currentSize > theLists.length) {
                rehash();
            }
        }
    }

    public void remove(AnyType x) {
        List<AnyType> which = theLists[myhash(x)];
        if (which.contains(x)) {
            which.remove(x);
            currentSize--;
        }
    }

    public boolean contains(AnyType x) {
        List<AnyType> which = theLists[myhash(x)];
        return which.contains(x);

    }

    public void makeEmpty() {
        for (int i = 0; i < theLists.length; i++) {
            theLists[i].clear();
        }
        currentSize = 0;
    }

    public void rehash() {
        List<AnyType>[] oldList = theLists;

        theLists = new List[newtPrime(2 * theLists.length)];
        for (int i = 0; i < theLists.length; i++) {
            theLists[i] = new LinkedList<>();
        }

        currentSize = 0;
        for (List<AnyType> item : oldList) {
            for (AnyType x : item) {
                insert(x);
            }
        }
    }

    public int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= theLists.length;
        if (hashVal < 0) {
            hashVal += theLists.length;
        }
        return hashVal;
    }


    private static int newtPrime(int size) {
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
