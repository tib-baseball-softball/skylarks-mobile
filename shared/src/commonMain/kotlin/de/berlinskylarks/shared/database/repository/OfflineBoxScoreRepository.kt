package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.BoxScoreDao
import de.berlinskylarks.shared.database.model.BoxScoreEntity
import kotlinx.coroutines.flow.Flow

class OfflineBoxScoreRepository(
    private val boxScoreDao: BoxScoreDao,
) : BoxScoreRepository {
    override suspend fun insertBoxScore(boxScore: BoxScoreEntity) = boxScoreDao.insert(boxScore)

    override fun getBoxScoreByMatchID(matchID: String): Flow<BoxScoreEntity?> =
        boxScoreDao.getBoxScoreByMatchID(matchID)
}