package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.BSMTeam

class BSMTeamsAPIClient(authKey: String) : BSMAPIClient(authKey) {
    suspend fun loadBSMTeamsForClub(clubID: Int, season: Int): List<BSMTeam> {
        return apiCall<List<BSMTeam>>(
            resource = "clubs/$clubID/teams.json",
            queryParameters = mutableListOf(
                SEASON_FILTER to season.toString()
            )
        ) ?: emptyList()
    }
}