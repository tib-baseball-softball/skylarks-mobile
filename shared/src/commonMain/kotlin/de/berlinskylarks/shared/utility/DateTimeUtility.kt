package de.berlinskylarks.shared.utility

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class DateTimeUtility {
    companion object {
        fun getAge(birthday: Long): Int {
            val birthInstant = Instant.Companion.fromEpochSeconds(birthday)

            val birthDate = birthInstant.toLocalDateTime(TimeZone.Companion.currentSystemDefault()).date
            val currentDate = Clock.System.todayIn(TimeZone.Companion.currentSystemDefault())

            var age = currentDate.year - birthDate.year

            if (currentDate.month < birthDate.month ||
                (currentDate.month == birthDate.month && currentDate.day < birthDate.day)
            ) {
                age--
            }
            return age
        }
    }
}