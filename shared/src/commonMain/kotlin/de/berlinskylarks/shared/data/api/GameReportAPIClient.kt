package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class GameReportAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String
) : TYPO3APIClient(configurationRepository, authKey) {
    suspend fun loadGameReports(): List<GameReport> {
        val response = apiCall<List<GameReport>>(
            resource = "api/v2/gamereports",
        )
        return response ?: emptyList()
    }

    suspend fun loadGameReportForBSMMatchID(matchID: String): GameReport? {
        val response = apiCall<List<GameReport>>(
            resource = "api/v2/gamereports",
            queryParameters = mutableListOf(
                "game_id" to matchID
            )
        )
        return response?.firstOrNull()
    }
}