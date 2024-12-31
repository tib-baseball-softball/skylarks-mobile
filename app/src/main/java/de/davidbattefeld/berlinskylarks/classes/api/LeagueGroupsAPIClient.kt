package de.davidbattefeld.berlinskylarks.classes.api

import de.davidbattefeld.berlinskylarks.model.LeagueGroup

class LeagueGroupsAPIClient: BSMAPIClient() {
    suspend fun loadLeagueGroupsForClub(season: Int?): List<LeagueGroup> {
        return apiCall<List<LeagueGroup>>(
            resource = "league_groups.json",
            queryParameters = mutableListOf(
                SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
            )
        ) ?: listOf()
    }
}