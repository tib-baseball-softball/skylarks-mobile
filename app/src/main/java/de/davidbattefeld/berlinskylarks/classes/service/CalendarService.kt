package de.davidbattefeld.berlinskylarks.classes.service

import android.content.ContentValues
import android.content.Context
import android.provider.CalendarContract
import model.Game
import java.time.ZoneId

class CalendarService {
    fun addGamesToCalendar(context: Context, games: List<Game>, calendarID: Int) {
        // TODO: move off main thread
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
                put(CalendarContract.Events.DESCRIPTION, "TODO: put details here")
                put(CalendarContract.Events.CALENDAR_ID, calendarID)
            }
            context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, calendarValues)
        }
    }
}