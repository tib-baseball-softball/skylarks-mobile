package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.ClubEntity
import kotlinx.coroutines.flow.Flow

interface ClubRepository {
    suspend fun insertClub(club: ClubEntity)
    fun getAllClubs(): Flow<List<ClubEntity>>
    fun getClubByID(id: Int): Flow<ClubEntity?>
}