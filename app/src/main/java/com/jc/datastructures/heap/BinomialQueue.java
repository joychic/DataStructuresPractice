package com.jc.datastructures.heap;

public class BinomialQueue<AnyType extends Comparable<? super AnyType>> {

    private static final int DEFAULT_TREES = 1;
    private int currentSize;
    private Node<AnyType>[] theTrees;

    private static class Node<AnyType> {
        AnyType element;
        Node<AnyType> leftChild;
        Node<AnyType> nextSibling;

        public Node(AnyType element) {
            this(element, null, null);
        }

        public Node(AnyType element, Node<AnyType> leftChild, Node<AnyType> nextSibling) {
            this.element = element;
            this.leftChild = leftChild;
            this.nextSibling = nextSibling;
        }
    }


    public BinomialQueue() {
        theTrees = new Node[DEFAULT_TREES];
        makeEmpty();
    }


    public BinomialQueue(AnyType item) {
        currentSize = 1;
        theTrees = new Node[1];
        theTrees[0] = new Node<>(item);
    }


    public void merge(BinomialQueue<AnyType> rhs) {
        if (this == rhs) {
            return;
        }
        currentSize += rhs.currentSize;
        if (currentSize > capacity()) {
            int maxLength = Math.max(theTrees.length, rhs.theTrees.length);
            expandTheTrees(maxLength + 1);
        }

        Node<AnyType> carry = null;
        for (int i = 0, j = i; j < currentSize; i++, j *= 2) {
            Node<AnyType> t1 = theTrees[i];
            Node<AnyType> t2 = i < rhs.theTrees.length ? rhs.theTrees[i] : null;

            int whichCase = t1 == null ? 0 : 1;
            whichCase += t2 == null ? 0 : 2;
            whichCase += carry == null ? 0 : 4;

            switch (whichCase) {
                case 0:
                case 1:
                    break;
                case 2:
                    theTrees[i] = t2;
                    rhs.theTrees[i] = null;
                    break;
                case 3:
                    carry = combineTrees(t1, t2);
                    theTrees[i] = rhs.theTrees[i] = null;
                    break;
                case 4:
                    theTrees[i] = carry;
                    carry = null;
                    break;
                case 5:
                    carry = combineTrees(t1, carry);
                    theTrees[i] = null;
                    break;
                case 6:
                    carry = combineTrees(t2, carry);
                    rhs.theTrees[i] = null;
                    break;
                case 7:
                    theTrees[i] = carry;
                    carry = combineTrees(t1, t2);
                    rhs.theTrees[i] = null;
                    break;

            }
        }

        for (int k = 0; k < rhs.theTrees.length; k++) {
            rhs.theTrees[k] = null;
        }
        rhs.currentSize = 0;
    }

    public void insert(AnyType x) {
        merge(new BinomialQueue<AnyType>(x));
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }
        return theTrees[findMinIndex()].element;

    }

    public AnyType deleteMin() {

        if (isEmpty()) {
            throw new UnsupportedOperationException();
        }

        int minIndex = findMinIndex();
        AnyType minItem = theTrees[minIndex].element;

        Node<AnyType> deletedTree = theTrees[minIndex].leftChild;


        BinomialQueue<AnyType> deletedQueue = new BinomialQueue<>();
        deletedQueue.expandTheTrees(minIndex + 1);

        deletedQueue.currentSize = (1 << minIndex) - 1;
        for (int j = minIndex - 1; j >= 0; j--) {
            deletedQueue.theTrees[j] = deletedTree;
            deletedTree = deletedTree.nextSibling;
            deletedQueue.theTrees[j].nextSibling = null;
        }


        theTrees[minIndex] = null;
        currentSize -= deletedQueue.currentSize + 1;

        merge(deletedQueue);

        return minItem;

    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void makeEmpty() {
        currentSize = 0;
        for (int i = 0; i < theTrees.length; i++) {
            theTrees[i] = null;
        }
    }


    private void expandTheTrees(int newNumTrees) {
        Node<AnyType>[] old = theTrees;
        int oldNumTrees = theTrees.length;

        theTrees = new Node[newNumTrees];
        for (int i = 0; i < Math.min(newNumTrees, oldNumTrees); i++) {
            theTrees[i] = old[i];
        }
        for (int i = oldNumTrees; i < newNumTrees; i++) {
            theTrees[i] = null;
        }
    }

    /**
     * 合并二叉树
     *
     * @param t1
     * @param t2
     * @return
     */
    private Node<AnyType> combineTrees(Node<AnyType> t1, Node<AnyType> t2) {
        if (t1.element.compareTo(t2.element) < 0) {
            return combineTrees(t2, t1);
        }
        t2.nextSibling = t1.leftChild;
        t1.leftChild = t2;
        return t1;
    }

    private int capacity() {
        return (1 << theTrees.length) - 1;
    }

    private int findMinIndex() {

        int i;
        int minIndex;
        for (i = 0; theTrees[i] == null; i++) {

        }

        for (minIndex = i; i < theTrees.length; i++) {
            if (theTrees[i] != null && theTrees[i].element.compareTo(theTrees[minIndex].element) < 0) {
                minIndex = i;
            }
        }

        return minIndex;

    }
}
