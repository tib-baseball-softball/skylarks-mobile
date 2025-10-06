package de.berlinskylarks.shared.utility

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.Instant

open class DateTimeUtility {
    companion object {
        const val BSM_DATE_TIME_FORMAT_PATTERN = "y-M-dd HH:mm:ss Z"

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
            return parseBSMDateTime(dateTimeString)
        }
    }
}

expect fun parseBSMDateTime(raw: String): Instant