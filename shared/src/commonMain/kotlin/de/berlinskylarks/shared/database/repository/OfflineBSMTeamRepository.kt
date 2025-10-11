package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.BSMTeamDao
import de.berlinskylarks.shared.database.model.BSMTeamEntity
import kotlinx.coroutines.flow.Flow

class OfflineBSMTeamRepository(
    private val bsmTeamDao: BSMTeamDao
) : BSMTeamRepository {
    override suspend fun insertTeam(team: BSMTeamEntity) = bsmTeamDao.insertTeam(team)

    override fun getAllTeams(): Flow<List<BSMTeamEntity>> = bsmTeamDao.getAllTeams()

    override fun getTeamByID(id: Int): Flow<BSMTeamEntity?> = bsmTeamDao.getTeamByID(id)

    override fun getTeamsBySeason(season: Int): Flow<List<BSMTeamEntity>> =
        bsmTeamDao.getTeamsBySeason(season)
}
