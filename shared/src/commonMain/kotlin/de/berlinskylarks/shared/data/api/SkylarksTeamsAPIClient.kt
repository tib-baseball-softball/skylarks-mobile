package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.tib.Player
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam

class SkylarksTeamsAPIClient(authKey: String) : TYPO3APIClient(authKey) {
    suspend fun loadAllTeams(): List<SkylarksTeam> {
        return apiCall<List<SkylarksTeam>>(
            resource = "api/v2/teams",
        ) ?: listOf()
    }

    suspend fun loadPlayersForTeam(teamID: Int): List<Player> {
        return apiCall<List<Player>>(
            resource = "api/v2/players",
            queryParameters = mutableListOf(
                "team" to teamID.toString()
            )
        ) ?: listOf()
    }

    suspend fun loadAllPlayers(): List<Player> {
        return apiCall<List<Player>>(
            resource = "api/v2/players",
        ) ?: listOf()
    }
}