package ru.skillbranch.devintensive.extensions

fun String.truncate(count: Int = 16): String {
    return when {
        this.trimEnd().length > count -> "${this.substring(0, count).trimEnd()}..."
        else -> this
    }
}

fun String.stripHtml(): String {
//    return this.replace(Regex("<[^>]*>"), "")
//    return this.replace(Regex("(<.*?>)|(&.*?;)|([ ]{2,})"), "")
    return this.trim().replace(Regex("<.*?>"), "").replace(Regex("&.*?;"), "").replace(Regex(" +"), " ")
}
