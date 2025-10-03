package de.berlinskylarks.shared.utility

import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.localeWithLocaleIdentifier
import platform.Foundation.timeIntervalSince1970
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(markerClass = [ExperimentalTime::class])
actual fun parseBSMDateTime(raw: String): Instant {
    val formatter = NSDateFormatter().apply {
        dateFormat = "yyyy-MM-dd HH:mm:ss Z"
        locale = NSLocale.localeWithLocaleIdentifier("en_US_POSIX")
    }
    val date = formatter.dateFromString(raw)
        ?: throw IllegalArgumentException("Failed to parse date: $raw")

    val epochSeconds = date.timeIntervalSince1970.toLong()
    val nanos = ((date.timeIntervalSince1970 - epochSeconds) * 1_000_000_000).toInt()
    return Instant.fromEpochSeconds(epochSeconds, nanos)
}