package de.berlinskylarks.shared.utility

import de.berlinskylarks.shared.data.enums.Gameday
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

class BSMUtility {
    companion object {
        /**
         * Concept: the `current` gameday ranges from Thursday before the weekend to the Wednesday after.
         */
        fun getDatesForGameday(gameday: Gameday): Pair<Instant, Instant> {
            val timeZone = TimeZone.currentSystemDefault()
            val currentMoment = Clock.System.now()
            val currentDateTime = currentMoment.toLocalDateTime(timeZone)

            var gamedayStart: Instant
            var calculationStartingPoint: Instant
            when (gameday) {
                Gameday.ANY -> {
                    return Pair(first = Instant.DISTANT_PAST, second = Instant.DISTANT_FUTURE)
                }

                Gameday.UPCOMING -> {
                    return Pair(first = currentMoment, second = Instant.DISTANT_FUTURE)
                }

                Gameday.CURRENT -> {
                    calculationStartingPoint = currentDateTime.toInstant(timeZone)
                }

                Gameday.NEXT -> {
                    calculationStartingPoint = currentDateTime.toInstant(timeZone).plus(7, DateTimeUnit.DAY, timeZone)
                }

                Gameday.PREVIOUS -> {
                    calculationStartingPoint = currentDateTime.toInstant(timeZone).minus(7, DateTimeUnit.DAY, timeZone)
                }
            }

            var counter = 0
            while (true) {
                gamedayStart = calculationStartingPoint.minus(counter, DateTimeUnit.DAY, timeZone)

                if (gamedayStart.toLocalDateTime(timeZone).dayOfWeek == DayOfWeek.THURSDAY) {
                    val gamedayEnd = gamedayStart.plus(6.days)
                    return Pair(first = gamedayStart, second = gamedayEnd)
                } else {
                    counter++
                }
            }
        }
    }
}