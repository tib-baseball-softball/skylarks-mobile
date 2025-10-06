package de.davidbattefeld.berlinskylarks.data.utility

import de.berlinskylarks.shared.utility.DateTimeUtility
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.Instant
import kotlin.time.toJavaInstant

class AndroidDateTimeUtility : DateTimeUtility() {
    companion object {
        fun formatDate(
            instant: Instant,
            style: FormatStyle? = FormatStyle.MEDIUM
        ): String {
            val zonedDateTime = instant.toJavaInstant().atZone(ZoneId.systemDefault())
            val formatter = DateTimeFormatter.ofLocalizedDateTime(style)

            return zonedDateTime.format(formatter)
        }
    }
}