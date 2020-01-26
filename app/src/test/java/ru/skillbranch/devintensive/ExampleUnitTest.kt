package ru.ubaka.myapplication

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.add
import ru.skillbranch.devintensive.extensions.format
import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.extensions.truncate
import ru.skillbranch.devintensive.models.*
import ru.skillbranch.devintensive.utils.Utils
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
    fun test_UserConstructor() {
        val userA = User("0");
        val userB = User("1", "Vova", "Popov")
    }

    @Test
    fun test_UserFactory() {
        val userA = User.makeUser("Pert Kozlov")
    }

    @Test
    fun test_UtilsParseFullName() {
        println(Utils.parseFullName(null))
        println(Utils.parseFullName(""))
        println(Utils.parseFullName(" "))
        println(Utils.parseFullName("Alex"))
        println(Utils.parseFullName("Roma Volkov"))
    }

    @Test
    fun test_DateFormat() {
        println(Date().format())
        println(Date().format("HH:mm"))
    }

    @Test
    fun test_DateAdd() {
        println(Date())
        println(Date().add(2, TimeUnits.SECOND))
        println(Date().add(-4, TimeUnits.DAY))
    }

    @Test
    fun test_MessageFactory() {
        val user = User.makeUser("Anton Glebov")
        val txtMessage = BaseMessage.makeMessage(user, Chat("1"), payload = "Hello :)", type = "text")
        val imgMessage = BaseMessage.makeMessage(user, Chat("1"), payload = "image url", type = "image")

        when (txtMessage) {
            is TextMessage -> println("txtMessage is Text")
            is ImageMessage -> println("txtMessage is Image")
        }

        println(txtMessage.formatMessage())
        println(imgMessage.formatMessage())
    }

    @Test
    fun test_UtilsToInitials() {
        println(Utils.toInitials("john", "doe")) //JD
        println(Utils.toInitials("John", null)) //J
        println(Utils.toInitials(null, null)) //null
        println(Utils.toInitials(" ", "")) //null
    }

    @Test
    fun test_UtilsTransliteration() {
        println(Utils.transliteration("Женя Стереотипов")) //Zhenya Stereotipov
        println(Utils.transliteration("Amazing Петр", "_")) //Amazing_Petr
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
