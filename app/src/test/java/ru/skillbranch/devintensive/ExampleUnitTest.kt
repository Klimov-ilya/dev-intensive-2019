package ru.skillbranch.devintensive

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.devintensive.models.User
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
    fun test_decomposition() {
        val user = User.makeUser("John Wick")
        fun getUserInfo() = user

        val (id, first, lastName) = getUserInfo()

        println("$id, $first, ${user.component3()}")
    }

}
