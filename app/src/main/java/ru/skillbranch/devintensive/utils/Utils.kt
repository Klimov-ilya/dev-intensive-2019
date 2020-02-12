package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts = fullName?.split(" ")
        if (parts.isNullOrEmpty()) return null to null
        val firstName = parts[0].let { name -> if (name.isNullOrEmpty()) null else name }
        val lastName = if (parts.size > 1) {
            parts[1].let { lastName -> if (lastName.isNullOrEmpty()) null else lastName }
        } else null

        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return ""
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        return ""
    }
}