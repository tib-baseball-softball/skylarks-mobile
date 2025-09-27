package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.BoxScoreEntity

interface BoxScoreRepository {
    suspend fun insertBoxScore(boxScore: BoxScoreEntity)
    suspend fun getBoxScoreByMatchID(matchID: String): BoxScoreEntity?
}