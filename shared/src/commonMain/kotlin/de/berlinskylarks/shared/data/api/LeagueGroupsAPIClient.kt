package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class LeagueGroupsAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String
) : BSMAPIClient(configurationRepository, authKey) {
    /**
     * Unfortunately this endpoint does not offer documented filters, so the response will be scoped to
     * the access rights of the API key.
     */
    suspend fun loadLeagueGroupsForClub(season: Int?): List<LeagueGroup> {
        return apiCall<List<LeagueGroup>>(
            resource = "league_groups.json",
            queryParameters = mutableListOf(
                SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
            )
        ) ?: listOf()
    }
}