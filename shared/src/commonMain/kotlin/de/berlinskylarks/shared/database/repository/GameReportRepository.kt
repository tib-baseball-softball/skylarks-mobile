package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.GameReportEntityWithMedia
import kotlinx.coroutines.flow.Flow

interface GameReportRepository {
    suspend fun insertGameReport(gameReport: GameReportEntity)

    /**
     * Retrieves a stream of game reports based on optional season and team filters.
     * @param season The season to filter by, or null for all seasons. Needs to be String for comparison with `strftime()` output.
     * @param team The team to filter by, or null for all teams.
     */
    fun getGameReportsStream(season: String?, team: Int?): Flow<List<GameReportEntityWithMedia>>
    fun getGameReportByGameID(gameID: String): Flow<GameReportEntityWithMedia?>
}