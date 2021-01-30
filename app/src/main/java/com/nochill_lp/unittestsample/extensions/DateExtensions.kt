package com.nochill_lp.unittestsample.extensions

import java.text.SimpleDateFormat
import java.util.*

/*
*
* @author Luca Pollastri 
* @version 1.0
*
*/

fun String.tryParseDate(pattern: String): Date? {
    return kotlin.runCatching {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        formatter.parse(this)
    }.getOrNull()
}

fun Date.format(pattern: String, locale: Locale): String {
    val formatter = SimpleDateFormat(pattern, locale)
    return formatter.format(this)
}