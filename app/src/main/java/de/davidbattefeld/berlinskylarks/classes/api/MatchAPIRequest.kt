package de.davidbattefeld.berlinskylarks.classes.api

import model.GameScore

class MatchAPIRequest: BSMAPIRequest() {
    suspend fun loadGamesForClub(
        season: Int?,
        gamedays: String?,
        showExternal: Boolean = false,
    ): List<GameScore> {

        // Gameday Filter
        var gamedayParam: String
        if (gamedays.isNullOrEmpty()) {
            gamedayParam = "&filters[gamedays][]=current"
        } else {
            gamedayParam = "&filters[gamedays][]=$gamedays"
        }

        return apiCall<List<GameScore>>(
            resource = "matches",
            season = season,
            filters = gamedayParam,
            search = "skylarks",
        )
    }
}