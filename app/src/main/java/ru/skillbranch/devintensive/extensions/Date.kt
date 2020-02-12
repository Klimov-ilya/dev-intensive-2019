package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.humanizeDiff(date: Date = Date()): String {
    return ""
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String =
    SimpleDateFormat(pattern, Locale("ru")).format(this)

fun Date.add(value: Int, unit: TimeUnits = TimeUnits.SECOND) : Date {
    var time = this.time

    time += when(unit) {
        TimeUnits.SECOND -> value * SECONDS
        TimeUnits.MINUTE -> value * MINUTES
        TimeUnits.HOUR -> value * HOURS
        TimeUnits.DAY -> value * DAYS
        else -> throw IllegalStateException()
    }
    this.time = time

    return this
}

const val SECONDS = 1000L
const val MINUTES = 60 * SECONDS
const val HOURS = 60 * MINUTES
const val DAYS = 24 * HOURS

enum class TimeUnits{
    SECOND, MINUTE, HOUR, DAY;
}