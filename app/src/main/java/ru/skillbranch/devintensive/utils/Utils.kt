package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.split(" ")
        if (parts.isNullOrEmpty()) return null to null
        val firstName = parts[0].let { name -> if (name.isEmpty()) null else name }
        val lastName = if (parts.size > 1) {
            parts[1].let { lastName -> if (lastName.isEmpty()) null else lastName }
        } else {
            null
        }
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var result = ""
        val array = payload.split(" ")

        when {
            array.size == 1 && array[0].isEmpty() -> return ""
            array.size == 2 && array[0].isEmpty() && array[1].isEmpty() -> return ""
        }
        var needSeparator = true
        array.forEachIndexed { index, word ->
            if (word.isEmpty()) {
                result += ""
                needSeparator = false
            } else {
                var curr = if (index == 0) "" else if (needSeparator) divider else ""
                word.forEach { symbol ->
                    curr += when (symbol) {
                        'ё', 'Ё' -> if (symbol.isUpperCase()) 'E' else 'e'
                        in 'а'..'я', in 'А'..'Я' -> getChar(symbol, symbol.isUpperCase())
                        else -> symbol
                    }
                }
                needSeparator = curr.isNotEmpty()
                result += curr
            }
        }
        return result
    }

    private fun getChar(char: Char, isUppercase: Boolean = false): String {
        val symbol = when (char.toLowerCase()) {
            'а' -> "a"
            'б' -> "b"
            'в' -> "v"
            'г' -> "g"
            'д' -> "d"
            'е' -> "e"
            'ж' -> "zh"
            'з' -> "z"
            'и' -> "i"
            'й' -> "i"
            'к' -> "k"
            'л' -> "l"
            'м' -> "m"
            'н' -> "n"
            'о' -> "o"
            'п' -> "p"
            'р' -> "r"
            'с' -> "s"
            'т' -> "t"
            'у' -> "u"
            'ф' -> "f"
            'х' -> "h"
            'ц' -> "c"
            'ч' -> "ch"
            'ш' -> "sh"
            'щ' -> "sh'"
            'ъ' -> ""
            'ы' -> "i"
            'ь' -> ""
            'э' -> "e"
            'ю' -> "yu"
            'я' -> "ya"
            else -> char.toString()
        }
        return if (isUppercase) symbol.capitalize() else symbol
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        if (firstName.isNullOrEmpty() && lastName.isNullOrEmpty()) return null
        val initials = "${firstName?.replace(" ", "")?.firstOrNull()?.toUpperCase()
            ?: ""}${lastName?.replace(" ", "")?.firstOrNull()?.toUpperCase() ?: ""}"
        return if (initials.isEmpty()) null else initials
    }
}