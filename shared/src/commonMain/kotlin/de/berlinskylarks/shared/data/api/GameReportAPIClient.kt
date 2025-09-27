package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.tib.GameReport

class GameReportAPIClient(authKey: String) : TYPO3APIClient(authKey) {
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