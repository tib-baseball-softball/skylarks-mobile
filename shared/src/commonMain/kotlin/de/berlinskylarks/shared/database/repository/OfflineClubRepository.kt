package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.ClubDao
import de.berlinskylarks.shared.database.model.ClubEntity
import kotlinx.coroutines.flow.Flow

class OfflineClubRepository(
    private val clubDao: ClubDao
) : ClubRepository {
    override suspend fun insertClub(club: ClubEntity) = clubDao.insert(club)

    override fun getAllClubs(): Flow<List<ClubEntity>> = clubDao.getAllClubs()

    override fun getClubByID(id: Int): Flow<ClubEntity?> = clubDao.getClubByID(id)
}