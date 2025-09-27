package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.BoxScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BoxScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(boxScore: BoxScoreEntity)

    @Query("SELECT * FROM box_scores WHERE matchID LIKE :matchID")
    fun getBoxScoreByMatchID(matchID: String): Flow<BoxScoreEntity?>
}