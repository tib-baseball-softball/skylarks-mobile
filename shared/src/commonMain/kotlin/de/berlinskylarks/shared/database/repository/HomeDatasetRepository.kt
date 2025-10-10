package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.HomeDatasetEntity
import kotlinx.coroutines.flow.Flow

interface HomeDatasetRepository {
    suspend fun insert(homeDataset: HomeDatasetEntity)
    fun getHomeDatasetsByTeamIDAndSeason(teamID: Int, season: Int): Flow<List<HomeDatasetEntity>>
}
