package ru.skillbranch.devintensive

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
        assertTrue(TimeUnits.SECOND.plural(1) == "1 секунду")
        assertTrue(TimeUnits.MINUTE.plural(4) == "4 минуты")
        assertTrue(TimeUnits.HOUR.plural(19) == "19 часов")
        assertTrue(TimeUnits.DAY.plural(222) == "222 дня")
    }

}
