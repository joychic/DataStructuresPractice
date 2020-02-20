package com.jc.datastructures.tree;

import java.nio.BufferUnderflowException;

/**
 * AVL 树是带有平衡条件的二叉查找树，它保证树的深度需是 O(logN)
 * 当插入的节点破坏了树的平衡性，可以通过旋转来调整，
 * 对必须调整的节点是一字型的情况一般采取单旋转的方式，对之字形的采取双旋转的方式
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {

    private static final int ALLOWED_IMBANCE = 1;


    private AvlNode<AnyType> root;

    public AvlTree() {
        this.root = null;
    }

    public void makeEmpty() {
        this.root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    public AnyType findMin() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMin(root).element;
    }

    public AnyType findMax() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return findMax(root).element;
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    public void remove(AnyType x) {
        remove(x, root);
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            printTree(root);
        }
    }


    private int height(AvlNode<AnyType> node) {
        return node == null ? -1 : node.height;
    }

    private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            return new AvlNode<>(x, null, null);
        }

        int compareResult = x.compareTo(t.element);
        if (compareResult > 0) {
            t.right = insert(x, t.right);
        } else if (compareResult < 0) {
            t.left = insert(x, t.left);
        }

        return balance(t);

    }

    private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
        if (t == null) {
            return t;
        }

        if (height(t.left) - height(t.right) > ALLOWED_IMBANCE) {
            if (height(t.left.left) >= height(t.left.right)) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleRotateWithLeftChild(t);
            }
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBANCE) {
            if (height(t.right.right) >= height(t.right.left)) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleRotateWithRightChild(t);
            }
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }


    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode<AnyType> doubleRotateWithLeftChild(AvlNode<AnyType> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }


    private AvlNode<AnyType> doubleRotateWithRightChild(AvlNode<AnyType> k3) {
        k3.right = rotateWithLeftChild(k3.right);
        return rotateWithRightChild(k3);
    }

    private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
        if (t == null) {
            return t;
        }

        int compareResult = x.compareTo(t.element);
        if (compareResult > 0) {
            t.right = remove(x, t.right);
        } else if (compareResult < 0) {
            t.left = remove(x, t.left);
        } else if (t.left != null && t.right != null) {
            //要删除的节点具有2个儿子节点，一般策略是找到所有后代节点中的最小值或最大值来调整
            t.element = (AnyType) findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            //要删除的节点只有一个儿子节点，使用儿子节点替换
            t = t.left != null ? t.left : t.right;
        }

        return balance(t);
    }

    public void printRoot() {
        if (isEmpty()) {
            System.out.println(" Empty tree");
        } else {
            System.out.println(" root node is :" + root.element);
        }
    }


    private static class AvlNode<AnyType> {
        AnyType element;
        AvlNode<AnyType> left;
        AvlNode<AnyType> right;
        int height;  // 用来标记节点的高度

        AvlNode(AnyType element) {
            this.element = element;
        }

        AvlNode(AnyType element, AvlNode<AnyType> left, AvlNode<AnyType> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }


    private boolean contains(AnyType x, AvlNode<AnyType> root) {
        if (root == null) {
            return false;
        }

        int result = x.compareTo(root.element);
        if (result > 0) {
            return contains(x, root.right);
        }

        if (result < 0) {
            return contains(x, root.left);
        }

        return true;
    }


    private AvlNode<AnyType> findMin(AvlNode<AnyType> root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root;
        }
        return findMin(root.left);
    }

    private AvlNode<AnyType> findMax(AvlNode<AnyType> root) {
        if (root == null) {
            return null;
        }

        if (root.right == null) {
            return root;
        }
        return findMax(root.right);
    }


    private void printTree(AvlNode<AnyType> root) {
        if (root != null) {
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }

    }
}
