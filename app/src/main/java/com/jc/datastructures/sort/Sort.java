package com.jc.datastructures.sort;

import java.util.ArrayList;

public class Sort {
    /**
     * 插入排序
     * 在第 N 次循环时，前 N-1 个元素已经排过序，当前循环只需要找到合适的插入位置将新元素添加进去
     *
     * @param a
     * @param <AnyType>
     */
    public static <AnyType extends Comparable<? super AnyType>> void insertionSort(AnyType[] a) {
        int j;
        for (int i = 1; i < a.length; i++) {
            AnyType temp = a[i];
            for (j = i; j > 0 && a[j - 1].compareTo(temp) > 0; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
    }


    /**
     * 使用希尔增量的希尔排序
     * 可以通过优化增量序列降低复杂度
     *
     * @param a
     * @param <AnyType>
     */
    public static <AnyType extends Comparable<? super AnyType>> void shellSort(AnyType[] a) {
        int j;
        for (int gap = a.length / 2; gap > 0; gap = gap / 2) {
            for (int i = gap; i < a.length; i++) {
                AnyType temp = a[i];
                for (j = i; j >= gap && temp.compareTo(a[j - gap]) < 0; j -= gap) {
                    a[j] = a[j - gap];
                }
                a[j] = temp;
            }
        }
    }


    /**
     * 堆排序
     */
    public static <AnyType extends Comparable<? super AnyType>> void heapSort(AnyType[] a) {
        new HeapSort().heapSort(a);
    }


    private static class HeapSort<AnyType extends Comparable<? super AnyType>> {

        public void heapSort(AnyType[] a) {

            // 构建 max 堆的过程 对每个根节点下滤
            for (int i = a.length / 2 - 1; i >= 0; i--) {
                percolateDown(a, i, a.length);
            }

            for (int i = a.length - 1; i > 0; i--) {
                swapReferences(a, 0, i);
                percolateDown(a, 0, i);
            }

        }

        private void swapReferences(AnyType[] a, int index1, int index2) {
            AnyType tmp = a[index1];
            a[index1] = a[index2];
            a[index2] = tmp;
        }

        private void percolateDown(AnyType[] a, int i, int n) {
            int child;
            AnyType tmp;
            for (tmp = a[i]; leftChild(i) < n; i = child) {
                child = leftChild(i);
                if (child != n - 1 && a[child].compareTo(a[child + 1]) < 0) {
                    child++;
                }
                if (tmp.compareTo(a[child]) < 0) {
                    a[i] = a[child];
                } else {
                    break;
                }
            }
            a[i] = tmp;
        }

        private int leftChild(int i) {
            return 2 * i + 1;
        }
    }


    /**
     * 归并排序
     * 引入一个新的数组，采用分治法
     *
     * @param <AnyType>
     */
    public static <AnyType extends Comparable<? super AnyType>> void mergeSort(AnyType[] a) {
        AnyType[] tmpArray = (AnyType[]) new Comparable[a.length];
        new MergeSort().mergeSort(a, tmpArray, 0, a.length - 1);
    }


    private static class MergeSort<AnyType extends Comparable<? super AnyType>> {

        public void mergeSort(AnyType[] a, AnyType[] tmpArray, int left, int right) {
            if (left < right) {
                int center = (left + right) / 2;
                mergeSort(a, tmpArray, left, center);
                mergeSort(a, tmpArray, center + 1, right);
                merge(a, tmpArray, left, center + 1, right);
            }
        }

        private void merge(AnyType[] a, AnyType[] tmpArray, int leftPos, int rightPos, int rightEnd) {
            int leftEnd = rightPos - 1;
            int tmpPos = leftPos;//标记数组的位置
            int numElements = rightEnd - leftPos + 1;

            // 左边元素分别于右边元素比较，将较小的元素移到临时数组中
            while (leftPos <= leftEnd && rightPos <= rightEnd) {
                if (a[leftPos].compareTo(a[rightPos]) <= 0) {
                    tmpArray[tmpPos++] = a[leftPos++];
                } else {
                    tmpArray[tmpPos++] = a[rightPos++];
                }
            }

            // 对右边元素已全部移出但是左边还有剩余的情况进行处理
            while (leftPos <= leftEnd) {
                tmpArray[tmpPos++] = a[leftPos++];
            }

            //对左边元素已全部移出但是右边还有剩余的情况进行处理，与上面的循环互异
            while (rightPos <= rightEnd) {
                tmpArray[tmpPos++] = a[rightPos++];
            }


            // 将排好序的数组copy回去
            for (int i = 0; i < numElements; i++, rightEnd--) {
                a[rightEnd] = tmpArray[rightEnd];
            }

        }
    }


    /**
     * 快速排序
     *
     * @param a
     * @param <AnyType>
     */
    public static <AnyType extends Comparable<? super AnyType>> void quickSort(AnyType[] a) {
        new QuickSort().quickSort(a, 0, a.length - 1);
    }


    private static class QuickSort<AnyType extends Comparable<? super AnyType>> {
        final int CUTOFF = 10;// 截止范围，对于很小的数组，快速排序不如插入排序

        public void quickSort(AnyType[] a, int left, int right) {
            if ((left + CUTOFF) < right) {
                AnyType pivot = median3(a, left, right);
                int i = left, j = right - 1;
                for (; ; ) {
                    while (a[++i].compareTo(pivot) < 0) {
                    }
                    while (a[--j].compareTo(pivot) > 0) {
                    }
                    if (i < j) {
                        swapReferences(a, i, j);
                    } else {
                        break;
                    }
                }
                swapReferences(a, i, right - 1);
                quickSort(a, left, i - 1);
                quickSort(a, i + 1, right);
            } else {
                insertionSort(a);
            }
        }

        /**
         * 三数中值分割法
         *
         * @param a
         * @param left
         * @param right
         * @return
         */
        private AnyType median3(AnyType[] a, int left, int right) {
            int center = (left + right) / 2;
            if (a[left].compareTo(a[center]) > 0) {
                swapReferences(a, left, center);
            }

            if (a[left].compareTo(a[right]) > 0) {
                swapReferences(a, left, right);
            }

            if (a[center].compareTo(a[right]) > 0) {
                swapReferences(a, center, right);
            }


            swapReferences(a, center, right - 1);
            return a[right - 1];
        }


        final void swapReferences(AnyType[] a, int index1, int index2) {
            AnyType tmp = a[index1];
            a[index1] = a[index2];
            a[index2] = tmp;
        }
    }


    /**
     * 变长字符串的基数排序
     * <p>
     * 同时按照字母和长度进行排序，字母优先
     *
     * @param arr
     * @param maxLen
     */
    public static void radixSort(String[] arr, int maxLen) {
        final int BUCKETS = 256;
        ArrayList<String>[] wordsByLength = new ArrayList[maxLen + 1];
        ArrayList<String>[] buckets = new ArrayList[BUCKETS];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
        for (int i = 0; i < wordsByLength.length; i++) {
            wordsByLength[i] = new ArrayList<>();
        }

        for (String s : arr) {
            wordsByLength[s.length()].add(s);
        }

        int idx = 0;
        for (ArrayList<String> words : wordsByLength) {
            for (String s : words) {
                arr[idx++] = s;
            }
        }

        // 对相同长度的 按照字母顺序排序
        int startIndex = arr.length;
        for (int pos = maxLen - 1; pos >= 0; pos--) {
            startIndex -= wordsByLength[pos + 1].size();

            for (int i = startIndex; i < arr.length; i++) {
                buckets[arr[i].charAt(pos)].add(arr[i]);
            }

            idx = startIndex;
            for (ArrayList<String> thisBucket : buckets) {
                for (String s : thisBucket) {
                    arr[idx++] = s;
                }
                thisBucket.clear();
            }
        }
    }

}
