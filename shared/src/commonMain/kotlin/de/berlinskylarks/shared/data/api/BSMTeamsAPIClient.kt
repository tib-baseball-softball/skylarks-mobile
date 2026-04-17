package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.BSMTeam
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class BSMTeamsAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String) : BSMAPIClient(configurationRepository, authKey) {
    suspend fun loadBSMTeamsForClub(clubID: Int, season: Int): List<BSMTeam> {
        return apiCall<List<BSMTeam>>(
            resource = "clubs/$clubID/teams.json",
            queryParameters = mutableListOf(
                SEASON_FILTER to season.toString()
            )
        ) ?: emptyList()
    }
}