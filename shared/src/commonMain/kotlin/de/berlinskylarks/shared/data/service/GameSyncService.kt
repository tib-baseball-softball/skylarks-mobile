package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.TeamGlobals
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.enums.Gameday
import de.berlinskylarks.shared.database.model.BoxScoreEntity
import de.berlinskylarks.shared.database.model.GameEntity
import de.berlinskylarks.shared.database.repository.BoxScoreRepository
import de.berlinskylarks.shared.database.repository.GameRepository
import de.berlinskylarks.shared.utility.DateTimeUtility
import kotlinx.coroutines.flow.firstOrNull
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GameSyncService(
    private val gameRepository: GameRepository,
    private val boxScoreRepository: BoxScoreRepository,
    private val gameClient: MatchAPIClient,
) {
    suspend fun syncGamesForSeason(season: Int): Int {
        val organizations =
            listOf(BSMAPIClient.BSVBB_ORGANIZATION_ID, BSMAPIClient.DBV_ORGANIZATION_ID)

        var totalGamesSynced = 0

        organizations.forEach { org ->
            val games = gameClient.loadAllGames(
                season = season,
                gamedays = Gameday.ANY.value,
                organizations = org,
                compact = true
            )

            games.forEach { game ->
                val dateTime = DateTimeUtility.parseBSMDateTimeString(game.time).toString()
                gameRepository.insertGame(
                    GameEntity(
                        id = game.id,
                        matchID = game.matchID,
                        leagueID = game.leagueID ?: game.league.id,
                        time = game.time,
                        season = season,
                        external = !(game.awayTeamName.contains(TeamGlobals.TEAM_NAME) || game.homeTeamName.contains(
                            TeamGlobals.TEAM_NAME
                        )),
                        json = game,
                        dateTime = dateTime,
                    )
                )
            }
            totalGamesSynced += games.size
        }
        return totalGamesSynced
    }

    suspend fun syncSingleBoxScore(matchID: String, id: Int): Boolean {
        val existingBoxScore = boxScoreRepository.getBoxScoreByMatchID(matchID).firstOrNull()
        if (existingBoxScore != null) {
            println("Found boxScore for matchID $matchID, return early.")
            return true
        }

        val boxScore = gameClient.getBoxScoreForGame(gameID = id)
        if (boxScore != null) {
            boxScoreRepository.insertBoxScore(
                BoxScoreEntity(
                    matchID = boxScore.header.matchID,
                    json = boxScore,
                )
            )
            println("Inserted boxScore for matchID $matchID")
            return true
        }
        println("No boxScore found for matchID $matchID")
        return false
    }
}