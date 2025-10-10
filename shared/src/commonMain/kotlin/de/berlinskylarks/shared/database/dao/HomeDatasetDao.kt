package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.HomeDatasetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HomeDatasetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(homeDataset: HomeDatasetEntity)

    @Query("SELECT * FROM home_datasets WHERE teamID = :teamID AND season = :season")
    fun getHomeDatasetsByTeamIDAndSeason(teamID: Int, season: Int): Flow<List<HomeDatasetEntity>>
}