package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.TiBTeamDao
import de.berlinskylarks.shared.database.model.TiBTeamEntity
import kotlinx.coroutines.flow.Flow

class OfflineTiBTeamRepository(
    private val tiBTeamDao: TiBTeamDao
) : TiBTeamRepository {
    override suspend fun insertTeam(team: TiBTeamEntity) = tiBTeamDao.insert(team)

    override fun getAllTeams(): Flow<List<TiBTeamEntity>> = tiBTeamDao.getAllTeams()

    override fun getTeamByID(id: Int): Flow<TiBTeamEntity?> = tiBTeamDao.getTeamByID(id)
}