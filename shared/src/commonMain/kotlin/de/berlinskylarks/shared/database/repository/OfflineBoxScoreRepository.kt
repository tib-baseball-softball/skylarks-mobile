package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.BoxScoreDao
import de.berlinskylarks.shared.database.model.BoxScoreEntity

class OfflineBoxScoreRepository(
    private val boxScoreDao: BoxScoreDao,
) : BoxScoreRepository {
    override suspend fun insertBoxScore(boxScore: BoxScoreEntity) = boxScoreDao.insert(boxScore)

    override suspend fun getBoxScoreByMatchID(matchID: String): BoxScoreEntity? =
        boxScoreDao.getBoxScoreByMatchID(matchID)
}