package de.davidbattefeld.berlinskylarks.classes.api

import model.SkylarksTeam

class TeamsAPIRequest: SkylarksAPIRequest() {
    suspend fun loadAllTeams(): List<SkylarksTeam> {
        return apiCall<List<SkylarksTeam>>(
            resource = "api/teams/read",
        )
    }
}