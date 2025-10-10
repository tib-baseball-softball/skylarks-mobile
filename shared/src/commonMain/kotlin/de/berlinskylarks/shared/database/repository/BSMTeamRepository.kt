package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.BSMTeamEntity
import kotlinx.coroutines.flow.Flow

interface BSMTeamRepository {
    suspend fun insertTeam(team: BSMTeamEntity)
    fun getAllTeams(): Flow<List<BSMTeamEntity>>
    fun getTeamByID(id: Int): Flow<BSMTeamEntity?>
}
