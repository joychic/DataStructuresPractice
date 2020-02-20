package com.jc.datastructures.tree;

import java.nio.BufferUnderflowException;

/**
 * 二叉查找树  平均深度 O（logN）
 *
 * 对于树中的每个节点X，它的左子树中所有项的值小于X中的项，而它的右子树中的所有项的值大于X中的项
 *
 * <p>
 * 关于泛型通配符：
 * 频繁往外读取内容的，适合用上界Extends。
 * 经常往里插入的，适合用下界Super。
 */
public class BinarySearchTree<AnyType extends Comparable<? super AnyType>> {
    private BinaryNode<AnyType> root;

    public BinarySearchTree() {
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
        if (isEmpty()){
            System.out.println("Empty tree");
        }else {
            printTree(root);
        }
    }

    public void printRoot() {
        if (isEmpty()) {
            System.out.println(" Empty tree");
        } else {
            System.out.println(" root node is :" + root.element);
        }
    }


    private boolean contains(AnyType x, BinaryNode<AnyType> root) {
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


    /**
     * 递归实现
     *
     * @param root
     * @return
     */
    private BinaryNode<AnyType> findMax(BinaryNode<AnyType> root) {
        if (root == null) {
            return null;
        }

        if (root.right == null) {
            return root;
        }
        return findMax(root.right);
    }

    /**
     * 非递归实现
     *
     * @param root
     * @return
     */
    private BinaryNode<AnyType> findMin(BinaryNode<AnyType> root) {
        if (root == null) {
            return null;
        }
        while (root.left != null) {
            root = root.left;
        }

        return root;

    }

    private BinaryNode<AnyType> insert(AnyType x, BinaryNode<AnyType> root) {

        if (root == null) {
            return new BinaryNode<>(x);
        }
        int compareResult = x.compareTo(root.element);
        if (compareResult > 0) {
            root.right = insert(x, root.right);
        } else if (compareResult < 0) {
            root.left = insert(x, root.left);
        }

        return root;

    }


    private BinaryNode<AnyType> remove(AnyType x, BinaryNode<AnyType> root) {
        if (root == null) {
            return root;
        }
        int compareResult = x.compareTo(root.element);
        if (compareResult > 0) {
            root.right = remove(x, root.right);
        } else if (compareResult < 0) {
            root.left = remove(x, root.left);
        } else if (root.left != null && root.right != null) {
            //要删除的节点具有2个儿子节点，一般策略是找到所有后代节点中的最小值或最大值来调整
            root.element = (AnyType) findMin(root.right).element;
            root.right = remove(root.element, root.right);
        } else {
            //要删除的节点只有一个儿子节点，使用儿子节点替换
            root = root.left != null ? root.left : root.right;
        }

        return root;
    }


    private void printTree(BinaryNode<AnyType> root) {
      if (root!=null){
          printTree(root.left);
          System.out.println(root.element);
          printTree(root.right);
      }

    }


    /**
     * 节点类
     *
     * @param <AnyType>
     */
    private static class BinaryNode<AnyType> {

        BinaryNode(AnyType element) {
            this(element, null, null);
        }

        BinaryNode(AnyType element, BinaryNode left, BinaryNode right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

        AnyType element;
        BinaryNode left;
        BinaryNode right;

    }
}
