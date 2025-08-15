package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.LeagueGroup

class LeagueGroupsAPIClient(authKey: String) : BSMAPIClient(authKey) {
    suspend fun loadLeagueGroupsForClub(season: Int?): List<LeagueGroup> {
        return apiCall<List<LeagueGroup>>(
            resource = "league_groups.json",
            queryParameters = mutableListOf(
                SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
            )
        ) ?: listOf()
    }
}