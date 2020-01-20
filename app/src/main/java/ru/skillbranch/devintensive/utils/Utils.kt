package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        var nameParts: List<String>? = null
        val tmpFullName: String = fullName?.trim() ?: ""

        if (tmpFullName.length > 0)
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
            initials.length == 0 -> null
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
}