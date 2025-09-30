package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {
    suspend fun insertPlayer(player: PlayerEntity)
    fun getAllPlayers(): Flow<List<PlayerEntity>>
    fun getPlayerByID(id: Int): Flow<PlayerEntity?>
    fun getPlayerByBSMID(bsmID: Int): Flow<PlayerEntity?>
}