package de.davidbattefeld.berlinskylarks.classes.api

import model.Player
import model.SkylarksTeam

class TeamsAPIRequest: SkylarksAPIRequest() {
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