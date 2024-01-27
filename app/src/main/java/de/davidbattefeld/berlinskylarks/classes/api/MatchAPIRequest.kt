package de.davidbattefeld.berlinskylarks.classes.api

import model.Game

class MatchAPIRequest: BSMAPIRequest() {
    suspend fun loadGamesForClub(
        season: Int?,
        gamedays: String?,
    ): List<Game> {
        return apiCall<List<Game>>(
            resource = "clubs/$CLUB_ID/matches.json",
            queryParameters = mapOf(
                SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
                GAMEDAY_FILTER to (gamedays ?: "current"),
            )
        )
    }
}