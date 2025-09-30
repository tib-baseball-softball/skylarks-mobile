package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.TiBTeamEntity
import kotlinx.coroutines.flow.Flow

interface TiBTeamRepository {
    suspend fun insertTeam(team: TiBTeamEntity)
    fun getAllTeams(): Flow<List<TiBTeamEntity>>
    fun getTeamByID(id: Int): Flow<TiBTeamEntity?>
}