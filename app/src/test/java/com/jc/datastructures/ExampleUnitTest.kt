package com.jc.datastructures

import com.jc.datastructures.sort.Sort
import com.jc.datastructures.sort.Sort.insertionSort
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun sort() {
        val size = 1000
        var a = IntArray(size)
        for (i in a.indices) {
            a[i] = i
        }

        for (theSeed in 0..20) {
            Collections.shuffle(a.toList())
        }

        insertionSort(a.toTypedArray())

    }


}
