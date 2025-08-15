package de.davidbattefeld.berlinskylarks.data.api

import de.davidbattefeld.berlinskylarks.data.model.Player
import de.davidbattefeld.berlinskylarks.data.model.SkylarksTeam

class TeamsAPIClient: SkylarksAPIClient() {
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
}