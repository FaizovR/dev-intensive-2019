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

fun Date.humanizeDiff(date: Date = Date()): String {
    var diff = date.time - this.time

    fun dif(type: Long, one: String = "", twoOrFour: String = "", other: String = "") : String {

        diff = diff.absoluteValue

        return when {
            (diff / type) in 5..20 -> other
            (diff / type) % 10  == 1L -> one
            (diff / type) % 10 in 2..4 -> twoOrFour
            else -> other
        }
    }

    if (diff > 0) {
        return when (diff) {
            in 0..1*SECOND -> "только что"
            in 1*SECOND..45*SECOND -> "несколько секунд назад"
            in 45*SECOND..75*SECOND -> "минуту назад"
            in 75*SECOND..45*MINUTE -> "${diff/ MINUTE}" +
                    " ${dif(MINUTE, "минуту", "минуты", "минут")} назад"
            in 45*MINUTE..75*MINUTE -> "час назад"
            in 75*MINUTE..22*HOUR -> "${diff/ HOUR}" +
                    " ${dif(HOUR, "час", "часа", "часов")} назад"
            in 22*HOUR..26*HOUR -> "день назад"
            in 26*HOUR..360*DAY -> "${diff/ DAY}" +
                    " ${dif(DAY, "день", "дня", "дней")} назад"
            else -> "более года назад"
        }
    } else {
        return when(-diff) {
            in 0..1*SECOND -> "только что"
            in 1*SECOND..45*SECOND -> "через несколько секунд"
            in 45*SECOND..75*SECOND -> "через минуту"
            in 75*SECOND..45*MINUTE -> "через ${-diff/ MINUTE}" +
                    " ${dif(MINUTE, "минуту", "минуты", "минут")}"
            in 45*MINUTE..75*MINUTE -> "через час"
            in 75*MINUTE..22*HOUR -> "через ${-diff/ HOUR}" +
                    " ${dif(HOUR, "час", "часа", "часов")}"
            in 22*HOUR..26*HOUR -> "через день"
            in 26*HOUR..360*DAY -> "через ${-diff/ DAY}" +
                    " ${dif(DAY, "день", "дня", "дней")}"
            else -> "более чем через год"
        }
    }
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

enum class TimeUnits(val one: String, val few: String, val many: String)  {
    SECOND("секунду", "секунды", "секунд"),
    MINUTE("минуту", "минуты", "минут"),
    HOUR("час", "часа", "часов"),
    DAY("день", "дня", "дней");

    fun plural(num: Int) : String {
        return "$num ${this.getAmount(num)}"
    }

    private fun getAmount(num: Int) : String {
        return when{
            num in 5..20 -> many
            num % 10 == 1  -> one
            num % 10 in 2..4  -> few
            else -> many
        }
    }
}