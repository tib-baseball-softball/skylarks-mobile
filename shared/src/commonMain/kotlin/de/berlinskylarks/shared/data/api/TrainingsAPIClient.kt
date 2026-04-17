package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.tib.Training
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class TrainingsAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String) : TYPO3APIClient(configurationRepository, authKey) {
    suspend fun loadTrainingTimesForTeam(teamID: Int): List<Training> {
        return apiCall<List<Training>>(
            resource = "api/v2/trainings",
            queryParameters = mutableListOf(
                "team" to teamID.toString()
            )
        ) ?: listOf()
    }
}