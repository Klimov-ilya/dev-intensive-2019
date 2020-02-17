package ru.skillbranch.devintensive

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.skillbranch.devintensive.extensions.TimeUnits
import ru.skillbranch.devintensive.extensions.plural
import ru.skillbranch.devintensive.extensions.stripHtml
import ru.skillbranch.devintensive.extensions.truncate
import ru.skillbranch.devintensive.models.User
import ru.skillbranch.devintensive.utils.Utils
import java.util.Date

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun test_factory() {
        val user = User.makeUser("John Wick")
        val user2 = user.copy(id = "2", lastName = "Cena", lastVisit = Date())
        print("$user\n$user2")
    }

    @Test
    fun test_parseFullName() {
        assertTrue("${Utils.parseFullName(null).first} ${Utils.parseFullName(null).second}" == "null null")
        assertTrue("${Utils.parseFullName("").first} ${Utils.parseFullName("").second}" == "null null")
        assertTrue("${Utils.parseFullName(" ").first} ${Utils.parseFullName(" ").second}" == "null null")
        assertTrue("${Utils.parseFullName("John").first} ${Utils.parseFullName(null).second}" == "John null")
    }

    @Test
    fun test_initials() {
        assertTrue(Utils.toInitials("Илья", "Климов") == "ИК")
        assertTrue(Utils.toInitials("Илья", "") == "И")
        assertTrue(Utils.toInitials("", "Климов") == "К")
        assertTrue(Utils.toInitials(" ", "Климов") == "К")
        assertTrue(Utils.toInitials(null, "Климов") == "К")
        assertTrue(Utils.toInitials(" ", "") == null)
        assertTrue(Utils.toInitials(null, null) == null)
        assertTrue(Utils.toInitials(" ", " ") == null)
        assertTrue(Utils.toInitials("", "") == null)
    }

    @Test
    fun test_transliterations() {
        assertTrue(Utils.transliteration("Женя Стереотипов") == "Zhenya Stereotipov")
        assertTrue(Utils.transliteration("Amazing Петр","_") == "Amazing_Petr")
    }

    @Test
    fun test_truncate() {
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate())
        println("Bender Bending Rodriguez — дословно «Сгибальщик Сгибающий Родригес»".truncate(15))
        println("A     ".truncate(3))
    }

    @Test
    fun test_strip() {
        val a = "<p class=\"title\">Образовательное             IT-сообщество Skill Branch</p>"
        println(a.stripHtml())
    }

    @Test
    fun test_plural() {
        assertEquals("0 секунд", TimeUnits.SECOND.plural(0))
        assertEquals("1 секунду", TimeUnits.SECOND.plural(1))
        assertEquals("2 секунды", TimeUnits.SECOND.plural(2))
        assertEquals("7 секунд", TimeUnits.SECOND.plural(7))
        assertEquals("14 секунд", TimeUnits.SECOND.plural(14))
        assertEquals("24 секунды", TimeUnits.SECOND.plural(24))
        assertEquals("102 секунды", TimeUnits.SECOND.plural(102))
        assertEquals("112 секунд", TimeUnits.SECOND.plural(112))
        assertEquals("122 секунды", TimeUnits.SECOND.plural(122))
        assertEquals("311 секунд", TimeUnits.SECOND.plural(311))

        assertEquals("0 минут", TimeUnits.MINUTE.plural(0))
        assertEquals("1 минуту", TimeUnits.MINUTE.plural(1))
        assertEquals("2 минуты", TimeUnits.MINUTE.plural(2))
        assertEquals("7 минут", TimeUnits.MINUTE.plural(7))
        assertEquals("14 минут", TimeUnits.MINUTE.plural(14))
        assertEquals("24 минуты", TimeUnits.MINUTE.plural(24))
        assertEquals("102 минуты", TimeUnits.MINUTE.plural(102))
        assertEquals("112 минут", TimeUnits.MINUTE.plural(112))
        assertEquals("122 минуты", TimeUnits.MINUTE.plural(122))
        assertEquals("311 минут", TimeUnits.MINUTE.plural(311))

        assertEquals("0 часов", TimeUnits.HOUR.plural(0))
        assertEquals("1 час", TimeUnits.HOUR.plural(1))
        assertEquals("2 часа", TimeUnits.HOUR.plural(2))
        assertEquals("7 часов", TimeUnits.HOUR.plural(7))
        assertEquals("14 часов", TimeUnits.HOUR.plural(14))
        assertEquals("24 часа", TimeUnits.HOUR.plural(24))
        assertEquals("102 часа", TimeUnits.HOUR.plural(102))
        assertEquals("112 часов", TimeUnits.HOUR.plural(112))
        assertEquals("122 часа", TimeUnits.HOUR.plural(122))
        assertEquals("311 часов", TimeUnits.HOUR.plural(311))

        assertEquals("0 дней", TimeUnits.DAY.plural(0))
        assertEquals("1 день", TimeUnits.DAY.plural(1))
        assertEquals("2 дня", TimeUnits.DAY.plural(2))
        assertEquals("7 дней", TimeUnits.DAY.plural(7))
        assertEquals("14 дней", TimeUnits.DAY.plural(14))
        assertEquals("24 дня", TimeUnits.DAY.plural(24))
        assertEquals("102 дня", TimeUnits.DAY.plural(102))
        assertEquals("112 дней", TimeUnits.DAY.plural(112))
        assertEquals("122 дня", TimeUnits.DAY.plural(122))
        assertEquals("311 дней", TimeUnits.DAY.plural(311))
        assertEquals("1234 дня", TimeUnits.DAY.plural(1234))
    }

}
