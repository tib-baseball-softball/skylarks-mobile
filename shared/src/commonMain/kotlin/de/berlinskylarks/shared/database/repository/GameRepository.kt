package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.model.MatchBoxScore

class GameRepository(
    private val matchAPIClient: MatchAPIClient,
) {
    suspend fun loadGameBoxScore(gameID: Int): MatchBoxScore? {
        return matchAPIClient.getBoxScoreForGame(gameID)
    }
}