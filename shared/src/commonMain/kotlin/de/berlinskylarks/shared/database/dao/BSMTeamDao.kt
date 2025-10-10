package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.BSMTeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BSMTeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: BSMTeamEntity)

    @Query("SELECT * FROM bsm_teams WHERE id = :id")
    fun getTeamByID(id: Int): Flow<BSMTeamEntity?>

    @Query("SELECT * FROM bsm_teams")
    fun getAllTeams(): Flow<List<BSMTeamEntity>>
}