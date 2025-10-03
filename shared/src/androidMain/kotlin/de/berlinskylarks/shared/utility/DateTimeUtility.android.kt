package de.berlinskylarks.shared.utility

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Instant
import kotlin.time.toKotlinInstant

actual fun parseBSMDateTime(raw: String): Instant {
    val formatter = DateTimeFormatter.ofPattern(DateTimeUtility.BSM_DATE_TIME_FORMAT_PATTERN)
    val parsedInstant = OffsetDateTime.parse(raw, formatter).toInstant()
    return parsedInstant.toKotlinInstant()
}