package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.tib.GameReport

class GameReportAPIClient(authKey: String): SkylarksAPIClient(authKey) {
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