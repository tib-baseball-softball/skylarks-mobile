package de.berlinskylarks.shared.database.repository

import androidx.room.RoomRawQuery
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.enums.Gameday
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

    override fun getGamesByFilter(
        leagueGroupID: Int?,
        external: Boolean?,
        season: Int?,
        gameday: Gameday?
    ): Flow<List<GameEntity>> {
        val queryBuilder = StringBuilder("SELECT * FROM games")
        val args = mutableListOf<Any>()
        val conditions = mutableListOf<String>()

        leagueGroupID?.let {
            conditions.add("leagueID = ?")
            args.add(it)
        }

        external?.let {
            conditions.add("external = ?")
            args.add(if (it) 1 else 0) // Room maps booleans to 1/0
        }

        season?.let {
            conditions.add("season = ?")
            args.add(it)
        }

//        gameday?.let {
//            conditions.add("gameday = ?")
//            args.add(it.value)
//        }

        if (conditions.isNotEmpty()) {
            queryBuilder.append(" WHERE ").append(conditions.joinToString(" AND "))
        }

        //queryBuilder.append(" ORDER BY date DESC")

        val query = RoomRawQuery(
            sql = queryBuilder.toString(),
            onBindStatement = { bind ->
                args.forEachIndexed { index, arg ->
                    when (arg) {
                        is String -> bind.bindText(index = index + 1, value = arg)
                        is Int -> bind.bindLong(index = index + 1, value = arg.toLong())
                        is Boolean -> bind.bindBoolean(index = index + 1, value = (arg))
                    }
                }
            }
        )

        return gameDao.getGamesByFilter(query)
    }

    override fun getGameByID(id: Int): Flow<GameEntity?> = gameDao.getGameByID(id)

    override fun getGameByMatchID(matchID: String): Flow<GameEntity?> =
        gameDao.getGameByMatchID(matchID)
}