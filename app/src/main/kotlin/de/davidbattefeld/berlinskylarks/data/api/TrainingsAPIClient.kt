package de.davidbattefeld.berlinskylarks.data.api

import de.davidbattefeld.berlinskylarks.data.model.Training

class TrainingsAPIClient: SkylarksAPIClient() {
    suspend fun loadTrainingTimesForTeam(teamID: Int): List<Training> {
        return apiCall<List<Training>>(
            resource = "api/v2/trainings",
            queryParameters = mutableListOf(
                "team" to teamID.toString()
            )
        ) ?: listOf()
    }
}