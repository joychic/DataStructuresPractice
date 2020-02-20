package com.jc.datastructures;

import com.jc.datastructures.hashing.QuadraticProbingHashTable;
import com.jc.datastructures.heap.BinaryHeap;
import com.jc.datastructures.heap.LeftistHeap;
import com.jc.datastructures.sort.Sort;
import com.jc.datastructures.tree.AvlTree;
import com.jc.datastructures.tree.BinarySearchTree;

import org.junit.Test;

import java.util.Random;


public class TreeTest {

    @org.junit.Test
    public void binarySearchTree() {
        BinarySearchTree tree = new BinarySearchTree();
        tree.insert(new Test(11));
        tree.insert(new Test(22));
        tree.insert(new Test(12));
        tree.insert(new Test(8));
        tree.insert(new Test(32));
        tree.insert(new Test(9));
        tree.insert(new Test(5));
        tree.insert(new Test(7));
        tree.printTree();
        tree.printRoot();
    }


    @org.junit.Test
    public void avlTree() {
        AvlTree tree = new AvlTree();
        tree.insert(new Test(11));
        tree.insert(new Test(22));
        tree.insert(new Test(12));
        tree.insert(new Test(8));
        tree.insert(new Test(32));
        tree.insert(new Test(9));
        tree.insert(new Test(5));
        tree.insert(new Test(7));
        tree.printTree();
        tree.printRoot();
    }


    private static class Test implements Comparable {

        int node;

        public Test(int node) {
            this.node = node;
        }

        @Override
        public int compareTo(Object o) {
            Test test = (Test) o;
            if (this.node > test.node) {
                return 1;
            } else if (this.node < test.node) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "Test{" +
                    "node=" + node +
                    '}';
        }
    }


    @org.junit.Test
    public void quadraticProbingHashTable() {
        QuadraticProbingHashTable<String> H = new QuadraticProbingHashTable<>();


        long startTime = System.currentTimeMillis();

        final int NUMS = 2000000;
        final int GAP = 37;

        System.out.println("Checking... (no more output means success)");


        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            H.insert("" + i);
        for (int i = GAP; i != 0; i = (i + GAP) % NUMS)
            if (H.insert("" + i))
                System.out.println("OOPS 111 !!! " + i);
        for (int i = 1; i < NUMS; i += 2)
            H.remove("" + i);

        for (int i = 2; i < NUMS; i += 2)
            if (!H.contains("" + i))
                System.out.println("Find fails " + i);

        for (int i = 1; i < NUMS; i += 2) {
            if (H.contains("" + i))
                System.out.println("OOPS 222 !!! " + i);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Elapsed time: " + (endTime - startTime));
    }


    @org.junit.Test
    public void binaryHeap() {
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>();
        for (int i = 37; i != 0; i = (i + 37) % numItems) {
            h.insert(i);
        }

        System.out.println("aaaaaa:" + h.toString());

        for (int i = 1; i < numItems; i++) {
            if (h.deleteMin() != i) {
                System.out.println("Oops! " + i);
            }
        }

        System.out.println("bbbbb:" + h.toString());
    }


    @org.junit.Test
    public void leftistHeap() {
        int numItems = 100;
        LeftistHeap<Integer> h = new LeftistHeap<>();
        LeftistHeap<Integer> h1 = new LeftistHeap<>();
        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems)
            if (i % 2 == 0)
                h1.insert(i);
            else
                h.insert(i);

        h.merge(h1);
        for (i = 1; i < numItems; i++)
            if (h.deleteMin() != i)
                System.out.println("Oops! " + i);
    }


    @org.junit.Test
    public void sort() {
        final int NUM_ITEMS = 1000;
        Integer[] a = new Integer[NUM_ITEMS];
        for (int i = 0; i < a.length; i++)
            a[i] = i;

        System.out.println("Start insertionSort");
        ArrayUtils.shuffle(a);
        Sort.insertionSort(a);
        checkSort(a);

        System.out.println("Start shellSort");
        ArrayUtils.shuffle(a);
        Sort.shellSort(a);
        checkSort(a);


        System.out.println("Start HeapSort");
        ArrayUtils.shuffle(a);
        Sort.heapSort(a);
        checkSort(a);


        System.out.println("Start MergeSort");
        ArrayUtils.shuffle(a);
        Sort.mergeSort(a);
        checkSort(a);


        System.out.println("Start QuickSort");
        ArrayUtils.shuffle(a);
        Sort.quickSort(a);
        checkSort(a);


        System.out.println("Start RadixSort");
        String[] str = new String[]{"sds", "dsds", "f", "oi", "pevd", "ffsa", "adwcge","aa","dsdd","dsde"};
        Sort.radixSort(str, 10);
        for (String s : str) {
            System.out.println(s);
        }
        System.out.println("Finish RadixSort");

    }


    private static void checkSort(Integer[] a) {
        for (int i = 0; i < a.length; i++)
            if (a[i] != i)
                System.out.println("Error at " + i + " ,error value is " + a[i]);
        System.out.println("Finished checksort");
    }


    public static class ArrayUtils {
        private static Random rand = new Random();

        private static <T> void swap(T[] a, int i, int j) {
            T temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        public static <T> void shuffle(T[] arr) {
            int length = arr.length;
            for (int i = length; i > 0; i--) {
                int randInd = rand.nextInt(i);
                swap(arr, randInd, i - 1);
            }
        }
    }


}
