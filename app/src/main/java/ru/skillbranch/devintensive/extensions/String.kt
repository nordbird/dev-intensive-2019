package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    return when {
        this.trimEnd().length > count -> "${this.substring(0, count).trimEnd()}..."
        else -> this
    }
}