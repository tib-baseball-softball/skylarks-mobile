package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.Training

class TrainingsAPIClient(authKey: String) : SkylarksAPIClient(authKey) {
    suspend fun loadTrainingTimesForTeam(teamID: Int): List<Training> {
        return apiCall<List<Training>>(
            resource = "api/v2/trainings",
            queryParameters = mutableListOf(
                "team" to teamID.toString()
            )
        ) ?: listOf()
    }
}