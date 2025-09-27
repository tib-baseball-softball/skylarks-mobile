package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.BoxScoreEntity
import kotlinx.coroutines.flow.Flow

interface BoxScoreRepository {
    suspend fun insertBoxScore(boxScore: BoxScoreEntity)
    fun getBoxScoreByMatchID(matchID: String): Flow<BoxScoreEntity?>
}