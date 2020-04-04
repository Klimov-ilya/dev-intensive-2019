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

fun Date.shortFormat(): String {
    val pattern = if (this.isSameDay(Date())) "HH:mm" else "dd:MM:yy"
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.isSameDay(date: Date): Boolean {
    val day1 = this.time / DAYS
    val day2 = date.time / DAYS
    return day1 == day2
}

const val SECONDS = 1000L
const val MINUTES = 60 * SECONDS
const val HOURS = 60 * MINUTES
const val DAYS = 24 * HOURS

enum class TimeUnits{
    SECOND, MINUTE, HOUR, DAY;
}

fun TimeUnits.plural(value: Int) : String {
    when(this) {
        TimeUnits.SECOND -> {
            return when {
                value == 1 -> "$value секунду"
                value in 2..4 || (value > 20 && value.toString()[value.toString().length - 2] != '1' && (value.toString().last() == '2' || value.toString().last() == '3' || value.toString().last() == '4')) -> "$value секунды"
                else -> "$value секунд"
            }
        }
        TimeUnits.MINUTE -> {
            return when {
                value == 1 -> "$value минуту"
                value in 2..4 || (value > 20 && value.toString()[value.toString().length - 2] != '1' && (value.toString().last() == '2' || value.toString().last() == '3' || value.toString().last() == '4')) -> "$value минуты"
                else -> "$value минут"
            }
        }
        TimeUnits.HOUR -> {
            return when {
                value == 1 -> "$value час"
                value in 2..4 || (value > 20 && value.toString()[value.toString().length - 2] != '1' && (value.toString().last() == '2' || value.toString().last() == '3' || value.toString().last() == '4')) -> "$value часа"
                else -> "$value часов"
            }
        }
        TimeUnits.DAY -> {
            return when {
                value == 1 -> "$value день"
                value in 2..4 || (value > 20 && value.toString()[value.toString().length - 2] != '1' && (value.toString().last() == '2' || value.toString().last() == '3' || value.toString().last() == '4')) -> "$value дня"
                else -> "$value дней"
            }
        }
        else -> throw IllegalStateException()
    }
}