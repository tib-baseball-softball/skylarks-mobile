package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.LeagueGroupEntity
import kotlinx.coroutines.flow.Flow

interface LeagueGroupRepository {
    suspend fun insertLeagueGroup(leagueGroup: LeagueGroupEntity)
    fun getAllLeagueGroups(): Flow<List<LeagueGroupEntity>>
    fun getLeagueGroupByID(id: Int): Flow<LeagueGroupEntity?>
    fun getLeagueGroupsBySeason(season: Int): Flow<List<LeagueGroupEntity>>
}