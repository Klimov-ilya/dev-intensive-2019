package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.humanizeDiff(date: Date = Date()): String {
    return ""
}

/*
* 0с - 1с "только что"

1с - 45с "несколько секунд назад"

45с - 75с "минуту назад"

75с - 45мин "N минут назад"

45мин - 75мин "час назад"

75мин 22ч "N часов назад"

22ч - 26ч "день назад"

26ч - 360д "N дней назад"

>360д "более года назад"
*
* */

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

fun TimeUnits.plural(value: Int) : String {
    when(this) {
        TimeUnits.SECOND -> {
            return when (value.toString().last()) {
                '0' -> "$value секунд"
                '1' -> "$value секунду"
                in '2'..'4' -> "$value секунды"
                else -> "$value секунд"
            }
        }
        TimeUnits.MINUTE -> {
            return when (value.toString().last()) {
                '0' -> "$value минут"
                '1' -> "$value минуту"
                in '2'..'4' -> "$value минуты"
                else -> "$value минут"
            }
        }
        TimeUnits.HOUR -> {
            return when (value.toString().last()) {
                '0' -> "$value часов"
                '1' -> "$value час"
                in '2'..'4' -> "$value часа"
                else -> "$value часов"
            }
        }
        TimeUnits.DAY -> {
            return when (value.toString().last()) {
                '0' -> "$value дней"
                '1' -> "$value день"
                in '2'..'4' -> "$value дня"
                else -> "$value дней"
            }
        }
        else -> throw IllegalStateException()
    }
}