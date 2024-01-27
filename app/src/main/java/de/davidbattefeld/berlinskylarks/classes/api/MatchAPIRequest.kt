package de.davidbattefeld.berlinskylarks.classes.api

import model.GameScore

class MatchAPIRequest: BSMAPIRequest() {
    suspend fun loadGamesForClub(
        season: Int?,
        gamedays: String?,
    ): List<GameScore> {
        return apiCall<List<GameScore>>(
            resource = "clubs/$CLUB_ID/matches.json",
            queryParameters = mapOf(
                SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
                GAMEDAY_FILTER to (gamedays ?: "current"),
            )
        )
    }
}