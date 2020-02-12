package ru.skillbranch.devintensive

import org.junit.Assert.assertTrue
import org.junit.Test
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

}
