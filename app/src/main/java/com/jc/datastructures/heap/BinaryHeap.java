package com.jc.datastructures.heap;

import java.nio.BufferUnderflowException;

/**
 * 二叉堆  一种优先队列
 * <p>
 * 结构性质：一颗被完全填满的二叉树
 * 堆序性质：在一个堆中，对于每一个节点 X，X 的父亲节点的关键字小于(或等于) X 中的关键字，根节点除外(它没有父亲)
 *
 * @param <AnyType>
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {
    private int currentSize;
    private AnyType[] array;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        this(DEFAULT_CAPACITY);
    }

    public BinaryHeap(int capacity) {
        currentSize = 0;
        array = (AnyType[]) new Comparable[capacity + 1];
    }

    public BinaryHeap(AnyType[] items) {
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + 2) * 11 / 10];
        int i = 1;
        for (AnyType item : items) {
            array[i++] = item;
        }
        buildHeap();
    }

    /**
     * 上滤法  新元素在堆中上滤直到找到正确的位置
     *
     * @param x
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1) {
            enlargeArray(array.length * 2 + 1);
        }
        int hole = ++currentSize;
        for (array[0] = x; x.compareTo(array[hole / 2]) < 0; hole /= 2) {
            array[hole] = array[hole / 2];
        }
        array[hole] = x;
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }

        return array[1];
    }

    public AnyType deleteMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);
        return minItem;

    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void makeEmpty() {
        currentSize = 0;
    }


    /**
     * 下滤 核心实现函数
     *
     * @param hole
     */
    private void percolateDown(int hole) {
        int child;
        AnyType tmp = array[hole];
        for (; hole * 2 <= currentSize; hole = child) {
            child = hole * 2;
            if (child != currentSize && array[child + 1].compareTo(array[child]) < 0) {
                child++;
            }
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        array[hole] = tmp;
    }

    private void buildHeap() {
        for (int i = currentSize / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++) {
            array[i] = old[i];
        }
    }

    @Override
    public String toString() {
        return "BinaryHeap{" +
                "currentSize=" + currentSize +
                '}';
    }
}
