package com.hazz.kotlinmvp

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.hazz.kotlinmvp", appContext.packageName)
    }

    @Test
    fun filterTo_mapTo() {
        val numbers = listOf("one", "two", "three", "four")
        val filterResults = mutableListOf<String>() // ⽬标对象
        numbers.filterTo(filterResults) { it.length > 3 }
        numbers.filterIndexedTo(filterResults) { index, _ -> index == 0 }
        println(filterResults) // 包含两个操作的结果 [three, four, one]

        // 为了⽅便起⻅，这些函数将⽬标集合返回了，因此您可以在函数调⽤的相应参数中直接创建它：
        // 将数字直接过滤到新的哈希集中，从⽽消除结果中的重复项
        val result = numbers.mapTo(HashSet()) { it.length }// [4, 5, 3]
        println("distinct item lengths are $result")
    }

    @Test
    fun minus_Plus() {
        data class Salary(var base: Int = 100) {
            override fun toString(): String = base.toString()
        }

        operator fun Salary.plus(other: Salary): Salary = Salary(base + other.base)
        operator fun Salary.minus(other: Salary): Salary = Salary(base - other.base)

        val s1 = Salary(10)
        val s2 = Salary(20)

        println(s1 + s2) // 30
        println(s1 - s2) // -10
    }
}
