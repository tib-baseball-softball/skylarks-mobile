package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.LeagueGroupDao
import de.berlinskylarks.shared.database.model.LeagueGroupEntity
import kotlinx.coroutines.flow.Flow

class OfflineLeagueGroupRepository(
    private val leagueGroupDao: LeagueGroupDao
) : LeagueGroupRepository {
    override suspend fun insertLeagueGroup(leagueGroup: LeagueGroupEntity) =
        leagueGroupDao.insert(leagueGroup)

    override fun getAllLeagueGroups(): Flow<List<LeagueGroupEntity>> =
        leagueGroupDao.getAllLeagueGroups()

    override fun getLeagueGroupByID(id: Int): Flow<LeagueGroupEntity?> =
        leagueGroupDao.getLeagueGroupByID(id)

    override fun getLeagueGroupsBySeason(season: Int): Flow<List<LeagueGroupEntity>> =
        leagueGroupDao.getLeagueGroupsBySeason(season)
}