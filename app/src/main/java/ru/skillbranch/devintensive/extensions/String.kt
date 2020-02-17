package ru.skillbranch.devintensive.extensions

fun String.truncate(size: Int = 16): String {
    val truncate = this.substring(0, size)
    return if (truncate.last() == ' ') {
        this.truncate(size - 1)
    } else {
        "$truncate..."
    }
}

fun String.stripHtml(): String = this.replace(Regex("<.*?>"),"").replace(Regex("\\s+"), " ")

