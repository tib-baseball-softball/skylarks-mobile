package de.davidbattefeld.berlinskylarks.classes.api

import de.davidbattefeld.berlinskylarks.model.Training

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