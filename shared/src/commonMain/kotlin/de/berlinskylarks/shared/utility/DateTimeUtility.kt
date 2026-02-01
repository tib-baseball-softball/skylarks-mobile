package de.berlinskylarks.shared.utility

import kotlin.time.Instant

open class DateTimeUtility {
    companion object {
        const val BSM_DATE_TIME_FORMAT_PATTERN = "y-M-dd HH:mm:ss Z"

        fun parseBSMDateTimeString(dateTimeString: String): Instant {
            return parseBSMDateTime(dateTimeString)
        }
    }
}

expect fun parseBSMDateTime(raw: String): Instant