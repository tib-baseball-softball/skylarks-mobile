package de.berlinskylarks.shared.utility

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class DateTimeUtility {
    companion object {
        const val BSM_DATE_TIME_FORMAT_PATTERN = "y-M-dd HH:mm:ss Z"

        @OptIn(FormatStringsInDatetimeFormats::class)
        val BSM_FORMAT = LocalDateTime.Format {
            DateTimeFormat.formatAsKotlinBuilderDsl(DateTimeComponents.Format {
                byUnicodePattern(BSM_DATE_TIME_FORMAT_PATTERN)
            })
        }

        fun getAge(birthday: Long): Int {
            val birthInstant = Instant.Companion.fromEpochSeconds(birthday)

            val birthDate =
                birthInstant.toLocalDateTime(TimeZone.Companion.currentSystemDefault()).date
            val currentDate = Clock.System.todayIn(TimeZone.Companion.currentSystemDefault())

            var age = currentDate.year - birthDate.year

            if (currentDate.month < birthDate.month ||
                (currentDate.month == birthDate.month && currentDate.day < birthDate.day)
            ) {
                age--
            }
            return age
        }

        fun parseBSMDateTimeString(dateTimeString: String): Instant {
            val date = BSM_FORMAT.parse(dateTimeString)
            return date.toInstant(timeZone = TimeZone.currentSystemDefault())
        }
    }
}