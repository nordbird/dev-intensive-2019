package ru.ubaka.myapplication

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.extensions.truncate
import java.util.*

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
    fun test_DateHumanizeDiff() {
        println(Date().add(-2, TimeUnits.HOUR).humanizeDiff()) //2 часа назад
        println(Date().add(-5, TimeUnits.DAY).humanizeDiff()) //5 дней назад
        println(Date().add(2, TimeUnits.MINUTE).humanizeDiff()) //через 2 минуты
        println(Date().add(7, TimeUnits.DAY).humanizeDiff()) //через 7 дней
        println(Date().add(-400, TimeUnits.DAY).humanizeDiff()) //более года назад
        println(Date().add(400, TimeUnits.DAY).humanizeDiff()) //более чем через год
    }

    @Test
    fun test_String_truncate() {
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate()) //Bender Bending R...
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15)) //Bender Bending...
        println("A     ".truncate(3)) //A
    }
}
