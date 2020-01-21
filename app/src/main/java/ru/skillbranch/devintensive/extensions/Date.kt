package ru.skillbranch.devintensive.extensions

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
        in 75 * SECOND..45 * MINUTE -> "${absDelta / MINUTE} минут"
        in 45 * MINUTE..75 * MINUTE -> "час"
        in 75 * MINUTE..22 * HOUR -> "${absDelta / HOUR} часов"
        in 22 * HOUR..26 * HOUR -> "день"
        in 26 * HOUR..360 * DAY -> "${absDelta / DAY} дней"
        else -> ""
    }

    return when {
        delta <- 360 * DAY -> "более чем через год"
        delta > 360 * DAY -> "более года назад"
        delta < 0 -> "через $base"
        else -> "$base назад"
    }
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY
}