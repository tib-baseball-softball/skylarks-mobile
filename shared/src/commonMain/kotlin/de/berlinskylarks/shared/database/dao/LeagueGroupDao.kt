package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.LeagueGroupEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LeagueGroupDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(leagueGroup: LeagueGroupEntity)

    @Query("SELECT * FROM league_groups LIMIT 1")
    fun getFirstItem(): Flow<LeagueGroupEntity?>

    @Query("SELECT * FROM league_groups")
    fun getAllLeagueGroups(): Flow<List<LeagueGroupEntity>>

    @Query("SELECT * FROM league_groups WHERE id = :id")
    fun getLeagueGroupByID(id: Int): Flow<LeagueGroupEntity?>

    @Query("SELECT * FROM league_groups WHERE season = :season")
    fun getLeagueGroupsBySeason(season: Int): Flow<List<LeagueGroupEntity>>
}