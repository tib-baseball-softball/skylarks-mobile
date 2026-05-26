package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.GameReportDao
import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.GameReportEntityWithMedia
import kotlinx.coroutines.flow.Flow

class OfflineGameReportRepository(
    private val gameReportDao: GameReportDao,
) : GameReportRepository {
    override suspend fun insertGameReport(gameReport: GameReportEntity) =
        gameReportDao.insert(gameReport)

    override fun getGameReportsStream(season: String?, team: Int?): Flow<List<GameReportEntityWithMedia>> =
        gameReportDao.getAllGameReports(season, team)

    override fun getGameReportByGameID(gameID: String): Flow<GameReportEntityWithMedia?> =
        gameReportDao.getGameReportByGameID(gameID)
}