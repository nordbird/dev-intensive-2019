package ru.skillbranch.devintensive.utils

import kotlin.math.absoluteValue
import kotlin.math.min

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var nameParts: List<String>? = null
        val tmpFullName: String = fullName?.trim() ?: ""

        if (tmpFullName.isNotEmpty())
            nameParts = tmpFullName.split(" ")

        val firstName = nameParts?.getOrNull(0)
        val lastName = nameParts?.getOrNull(1)

        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val tmpFirstName: String = firstName?.trim()?.getOrNull(0)?.toUpperCase()?.toString() ?: ""
        val tmpLastName: String = lastName?.trim()?.getOrNull(0)?.toUpperCase()?.toString() ?: ""

        val initials: String = tmpFirstName + tmpLastName

        return when {
            initials.isEmpty() -> null
            else -> initials
        }
    }

    fun transliteration(payload: String?, divider: String = " "): String? {
        var result: String? = ""

        payload?.forEach {

            val needTransform = (it != it.toLowerCase())

            val ch = when (it.toLowerCase().toString()) {
                "а" -> "a"
                "б" -> "b"
                "в" -> "v"
                "г" -> "g"
                "д" -> "d"
                "е" -> "e"
                "ё" -> "e"
                "ж" -> "zh"
                "з" -> "z"
                "и" -> "i"
                "й" -> "i"
                "к" -> "k"
                "л" -> "l"
                "м" -> "m"
                "н" -> "n"
                "о" -> "o"
                "п" -> "p"
                "р" -> "r"
                "с" -> "s"
                "т" -> "t"
                "у" -> "u"
                "ф" -> "f"
                "х" -> "h"
                "ц" -> "c"
                "ч" -> "ch"
                "ш" -> "sh"
                "щ" -> "sh'"
                "ъ" -> ""
                "ы" -> "i"
                "ь" -> ""
                "э" -> "e"
                "ю" -> "yu"
                "я" -> "ya"
                " " -> divider
                else -> it.toString()
            }

            result += when (needTransform) {
                true -> ch.capitalize()
                else -> ch
            }
        }

        return result
    }

    fun pluralForm(value: Long, forms: List<String>?): String {
        val cases: IntArray = intArrayOf(2, 0, 1, 1, 1, 2)
        if (forms?.size != 3) {
            return ""
        }

        val index: Int
        val absValue = value.absoluteValue

        if ((absValue % 100 > 4) && (absValue % 100 < 20)) {
            index = 2
        } else {
            index = cases[min(absValue % 10, 5).toInt()]
        }

        return "$value ${forms[index]}"
    }

    fun isValidGitHubURL(url: String): Boolean {
        val str = url.trim()
        return str.isEmpty() ||
                str.matches(Regex("""(https://)?(www.)?github.com/(?!enterprise|features|topics|collections|trending|events|marketplace|pricing|nonprofit|customer-stories|security|login|join)(\w*[^/])"""))
    }
}