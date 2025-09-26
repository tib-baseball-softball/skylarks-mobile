package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.GameReportEntityWithMedia
import kotlinx.coroutines.flow.Flow

@Dao
interface GameReportDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gameReport: GameReportEntity)

    @Transaction
    @Query("SELECT * FROM game_reports")
    fun getAllGameReports(): Flow<List<GameReportEntityWithMedia>>

    /**
     * The field `gameID` can contain multiple values separated by a comma.
     */
    @Transaction
    @Query("SELECT * FROM game_reports WHERE gameID LIKE :gameID")
    fun getGameReportByGameID(gameID: String): Flow<GameReportEntityWithMedia?>
}