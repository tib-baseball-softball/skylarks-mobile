package de.davidbattefeld.berlinskylarks.classes.api

import model.LeagueGroup

class LeagueGroupsAPIRequest: BSMAPIRequest() {
    suspend fun loadLeagueGroupsForClub(season: Int?): List<LeagueGroup> {
        return apiCall<List<LeagueGroup>>(
            resource = "league_groups.json",
            queryParameters = mapOf(
                SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
            )
        )
    }
}