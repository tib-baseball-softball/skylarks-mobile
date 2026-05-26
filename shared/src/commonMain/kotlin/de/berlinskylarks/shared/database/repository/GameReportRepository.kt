package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.GameReportEntityWithMedia
import kotlinx.coroutines.flow.Flow

interface GameReportRepository {
    suspend fun insertGameReport(gameReport: GameReportEntity)
    fun getGameReportsStream(season: String?, team: Int?): Flow<List<GameReportEntityWithMedia>>
    fun getGameReportByGameID(gameID: String): Flow<GameReportEntityWithMedia?>
}