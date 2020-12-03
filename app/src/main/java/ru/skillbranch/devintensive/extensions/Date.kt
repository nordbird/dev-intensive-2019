package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.utils.Utils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.shortFormat(): String {
    val pattern = if (this.isSameDay(Date())) "HH:mm" else "dd.MM.yy"
    return format(pattern)
}

fun Date.isSameDay(date: Date): Boolean {
    val day1 = this.time / DAY
    val day2 = date.time / DAY
    return day1 == day2
}

fun Date.add(value: Int, units: TimeUnits): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
    }

    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val delta = date.time - this.time
    val absDelta = delta.absoluteValue

    val base = when (absDelta) {
        in 0..SECOND -> "только что"
        in SECOND..45 * SECOND -> "несколько секунд"
        in 45 * SECOND..75 * SECOND -> "минуту"
        in 75 * SECOND..45 * MINUTE -> TimeUnits.MINUTE.plural(absDelta.toInt() / MINUTE.toInt())
        in 45 * MINUTE..75 * MINUTE -> "час"
        in 75 * MINUTE..22 * HOUR -> TimeUnits.HOUR.plural(absDelta.toInt() / HOUR.toInt())
        in 22 * HOUR..26 * HOUR -> "день"
        in 26 * HOUR..360 * DAY -> TimeUnits.DAY.plural(absDelta.toInt() / DAY.toInt())
        else -> ""
    }

    return when {
        delta < -360 * DAY -> "более чем через год"
        delta > 360 * DAY -> "более года назад"
        delta < 0 -> "через $base"
        else -> "$base назад"
    }
}

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int) = Utils.pluralForm(value, listOf("секунду", "секунды", "секунд"))
    },
    MINUTE {
        override fun plural(value: Int) = Utils.pluralForm(value, listOf("минуту", "минуты", "минут"))
    },
    HOUR {
        override fun plural(value: Int) = Utils.pluralForm(value, listOf("час", "часа", "часов"))
    },
    DAY {
        override fun plural(value: Int) = Utils.pluralForm(value, listOf("день", "дня", "дней"))
    };

    abstract fun plural(value: Int): String
}