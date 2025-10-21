package de.davidbattefeld.berlinskylarks.data.utility

import de.berlinskylarks.shared.utility.DateTimeUtility
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
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

        fun parseDateOrNull(dateStr: String?): LocalDate? {
            if (dateStr.isNullOrBlank()) return null
            val patterns = listOf(
                "yyyy-M-dd",
                "yyyy-MM-dd",
                "y-M-dd"
            )
            for (p in patterns) {
                try {
                    return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(p))
                } catch (_: DateTimeParseException) {
                }
            }
            return null
        }

        fun formatDateHuman(dateStr: String?): String {
            val date = parseDateOrNull(dateStr) ?: return "Unknown"
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return date.format(formatter)
        }

        fun relativeTime(dateStr: String?): String {
            val date = parseDateOrNull(dateStr) ?: return "Unknown"
            val today = LocalDate.now(ZoneId.systemDefault())
            val days = ChronoUnit.DAYS.between(today, date)
            return when {
                days > 0 -> "in $days days"
                days < 0 -> "${-days} days ago"
                else -> "today"
            }
        }
    }
}