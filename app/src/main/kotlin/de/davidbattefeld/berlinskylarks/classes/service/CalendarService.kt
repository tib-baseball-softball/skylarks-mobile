package de.davidbattefeld.berlinskylarks.classes.service

import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import de.davidbattefeld.berlinskylarks.classes.UserCalendar
import de.davidbattefeld.berlinskylarks.model.Game
import java.time.ZoneId

/**
 * Calendar-related operations. Permissions handling is carried out in the composables calling the
 * class methods.
 */
class CalendarService {
    var context: Context? = null

    /**
     * Loads all available calendars and returns them
     */
    fun loadUserCalendars(): List<UserCalendar> {
        val list = mutableListOf<UserCalendar>()
        val projection = arrayOf(
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.ACCOUNT_NAME
        )

        val cursor = context?.contentResolver?.query(
            CalendarContract.Calendars.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val calendarId =
                    it.getLong(it.getColumnIndexOrThrow(CalendarContract.Calendars._ID))
                val displayName =
                    it.getString(it.getColumnIndexOrThrow(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME))
                val accountName =
                    it.getString(it.getColumnIndexOrThrow(CalendarContract.Calendars.ACCOUNT_NAME))

                list.add(
                    UserCalendar(
                        id = calendarId,
                        displayName = displayName,
                        accountName = accountName
                    )
                )
            }
        }
        return list
    }

    /**
     * Adds the provided list of games to single calendar specified by ID.
     */
    fun addGamesToCalendar(games: List<Game>, calendarID: Long) {
        for (game in games) {
            val localDateTime = game.parseGameTimeString()
            val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
            val timeInMillis: Long = zonedDateTime.toInstant().toEpochMilli()

            val calendarValues = ContentValues().apply {
                put(CalendarContract.Events.DTSTART, timeInMillis)
                put(CalendarContract.Events.DTEND, timeInMillis + 120 * 60 * 1000) // 2 hours later
                put(
                    CalendarContract.Events.TITLE,
                    "${game.league.acronym}: ${game.away_team_name} @ ${game.home_team_name}"
                )

                val eventDescription =
"""
League: ${game.league.name}
Match Number: ${game.match_id}

Field: ${game.field?.name ?: "No data"}
Address: ${game.field?.street ?: ""}, ${game.field?.postal_code ?: ""} ${game.field?.city ?: ""}
"""

                put(CalendarContract.Events.DESCRIPTION, eventDescription)
                put(CalendarContract.Events.CALENDAR_ID, calendarID)
                put(CalendarContract.Events.EVENT_TIMEZONE, ZoneId.systemDefault().id)
                put(
                    CalendarContract.Events.EVENT_LOCATION,
                    "${game.field?.name} - ${game.field?.street ?: ""}, ${game.field?.postal_code ?: ""} ${game.field?.city ?: ""}\n (Lat: ${game.field?.latitude}, Long: ${game.field?.longitude})"
                )
            }
            context?.contentResolver?.insert(CalendarContract.Events.CONTENT_URI, calendarValues)
        }
    }
}