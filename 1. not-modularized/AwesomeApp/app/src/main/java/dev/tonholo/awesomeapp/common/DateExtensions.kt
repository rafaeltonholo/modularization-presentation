package dev.tonholo.awesomeapp.common

import java.text.SimpleDateFormat
import java.util.*

fun String.toReadableDate(): String {
    val locale = Locale.getDefault()
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", locale)
    return SimpleDateFormat("dd MMM, HH:mm", locale).run {
        parser.parse(this@toReadableDate)?.let { format(it) }
    }.orEmpty()
}
