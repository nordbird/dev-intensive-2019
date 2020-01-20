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
}