package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.PlayerDao
import de.berlinskylarks.shared.database.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

class OfflinePlayerRepository(
    private val playerDao: PlayerDao
) : PlayerRepository {
    override suspend fun insertPlayer(player: PlayerEntity) = playerDao.insert(player)

    override fun getAllPlayers(): Flow<List<PlayerEntity>> = playerDao.getAllPlayers()

    override fun getPlayerByID(id: Int): Flow<PlayerEntity?> = playerDao.getPlayerByID(id)

    override fun getPlayerByBSMID(bsmID: Int): Flow<PlayerEntity?> =
        playerDao.getPlayerByBSMID(bsmID)
}
