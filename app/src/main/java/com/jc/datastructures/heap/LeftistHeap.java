package com.jc.datastructures.heap;

import java.nio.BufferUnderflowException;

/**
 * 左式堆   广泛用于 merge 操作的一种数据结构
 */
public class LeftistHeap<AnyType extends Comparable<? super AnyType>> {

    private Node<AnyType> root;

    public LeftistHeap() {
        this.root = null;
    }

    public void merge(LeftistHeap<AnyType> rhs) {
        if (this == rhs) {
            return;
        }
        root = merge(root, rhs.root);
        rhs.root = null;
    }

    public void insert(AnyType x) {
        root = merge(new Node<>(x), root);
    }

    public AnyType findMin() {
        if (isEmpty()) {
            return null;
        }

        return root.element;
    }

    public AnyType deleteMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        AnyType minItem = root.element;
        root = merge(root.left, root.right);
        return minItem;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void makeEmpty() {
        root = null;
    }


    private static class Node<AnyType> {
        AnyType element;
        Node<AnyType> left;
        Node<AnyType> right;
        int npl;

        public Node(AnyType element) {
            this(element, null, null);
        }

        public Node(AnyType element, Node<AnyType> left, Node<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
            this.npl = 0;
        }
    }


    private Node<AnyType> merge(Node<AnyType> h1, Node<AnyType> h2) {
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }
        if (h1.element.compareTo(h2.element) < 0) {
            return realMerge(h1, h2);
        } else {
            return realMerge(h2, h1);
        }

    }

    private Node<AnyType> realMerge(Node<AnyType> h1, Node<AnyType> h2) {
        if (h1.left == null) {
            h1.left = h2;
        } else {
            h1.right = merge(h1.right, h2);
            if (h1.left.npl < h1.right.npl) {
                swapChildren(h1);
            }
            h1.npl = h1.right.npl + 1;
        }
        return h1;

    }

    private void swapChildren(Node<AnyType> t) {
        Node temp = t.left;
        t.left = t.right;
        t.right = temp;
    }

}
