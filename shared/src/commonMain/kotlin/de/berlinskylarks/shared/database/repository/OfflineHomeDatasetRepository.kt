package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.HomeDatasetDao
import de.berlinskylarks.shared.database.model.HomeDatasetEntity
import kotlinx.coroutines.flow.Flow

class OfflineHomeDatasetRepository(
    private val homeDatasetDao: HomeDatasetDao
) : HomeDatasetRepository {
    override suspend fun insert(homeDataset: HomeDatasetEntity) = homeDatasetDao.insert(homeDataset)

    override fun getHomeDatasetsByTeamIDAndSeason(teamID: Int, season: Int): Flow<List<HomeDatasetEntity>> =
        homeDatasetDao.getHomeDatasetsByTeamIDAndSeason(teamID, season)
}
