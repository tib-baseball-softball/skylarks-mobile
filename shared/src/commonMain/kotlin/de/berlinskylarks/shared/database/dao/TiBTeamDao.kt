package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.TiBTeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TiBTeamDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: TiBTeamEntity)

    @Query("SELECT * FROM tib_teams")
    fun getAllTeams(): Flow<List<TiBTeamEntity>>

    @Query("SELECT * FROM tib_teams WHERE id = :id")
    fun getTeamByID(id: Int): Flow<TiBTeamEntity?>
}