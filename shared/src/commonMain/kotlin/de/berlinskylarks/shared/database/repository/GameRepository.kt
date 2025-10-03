package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.data.enums.Gameday
import de.berlinskylarks.shared.database.model.GameEntity
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    suspend fun insertGame(game: GameEntity)
    fun getAllGames(): Flow<List<GameEntity>>
    fun getGamesByFilter(
        leagueGroupID: Int? = null,
        external: Boolean? = null,
        season: Int? = null,
        gameday: Gameday? = Gameday.CURRENT,
    ): Flow<List<GameEntity>>

    fun getGameByID(id: Int): Flow<GameEntity?>
    fun getGameByMatchID(matchID: String): Flow<GameEntity?>
}