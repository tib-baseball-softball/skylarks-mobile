package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.BoxScoreEntity

@Dao
interface BoxScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(boxScore: BoxScoreEntity)

    @Query("SELECT * FROM box_scores WHERE matchID LIKE :matchID")
    suspend fun getBoxScoreByMatchID(matchID: String): BoxScoreEntity?
}