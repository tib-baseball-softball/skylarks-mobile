package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.model.MatchBoxScore
import de.berlinskylarks.shared.database.dao.GameDao
import de.berlinskylarks.shared.database.model.GameEntity
import kotlinx.coroutines.flow.Flow

class OfflineGameRepository(
    private val matchAPIClient: MatchAPIClient,
    private val gameDao: GameDao
) : GameRepository {
    suspend fun loadGameBoxScore(gameID: Int): MatchBoxScore? {
        return matchAPIClient.getBoxScoreForGame(gameID)
    }

    override suspend fun insertGame(game: GameEntity) = gameDao.insert(game)

    override fun getAllGames(): Flow<List<GameEntity>> = gameDao.getAllGames()

    override fun getGameByID(id: Int): Flow<GameEntity?> = gameDao.getGameByID(id)

    override fun getGameByMatchID(matchID: String): Flow<GameEntity?> =
        gameDao.getGameByMatchID(matchID)
}