package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.TeamGlobals
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.enums.Gameday
import de.berlinskylarks.shared.database.model.GameEntity
import de.berlinskylarks.shared.database.repository.GameRepository

class GameSyncService(
    private val gameRepository: GameRepository,
    private val gameClient: MatchAPIClient,
) {
    suspend fun syncGamesForSeason(season: Int) {
        val games = gameClient.loadAllGames(
            season = season,
            gamedays = Gameday.ANY.value,
        )

        games.forEach { game ->
            gameRepository.insertGame(
                GameEntity(
                    id = game.id,
                    matchID = game.matchID,
                    leagueID = game.leagueID,
                    time = game.time,
                    season = game.season,
                    external = !(game.awayTeamName.contains(TeamGlobals.TEAM_NAME) || game.homeTeamName.contains(
                        TeamGlobals.TEAM_NAME
                    )),
                    json = game,
                )
            )
        }
    }
}