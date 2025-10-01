package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: PlayerEntity)

    @Query("SELECT * FROM players")
    fun getAllPlayers(): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE teams LIKE '%' || :teamID || '%'")
    fun getPlayersForTeam(teamID: Int): Flow<List<PlayerEntity>>

    @Query("SELECT * FROM players WHERE id = :id")
    fun getPlayerByID(id: Int): Flow<PlayerEntity?>

    @Query("SELECT * FROM players WHERE bsmID = :bsmID")
    fun getPlayerByBSMID(bsmID: Int): Flow<PlayerEntity?>
}