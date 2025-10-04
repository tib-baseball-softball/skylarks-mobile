package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.TeamGlobals
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.enums.Gameday
import de.berlinskylarks.shared.database.model.GameEntity
import de.berlinskylarks.shared.database.repository.GameRepository
import de.berlinskylarks.shared.utility.DateTimeUtility
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class GameSyncService(
    private val gameRepository: GameRepository,
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
}