package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.enums.Gameday
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.data.model.MatchBoxScore

/**
 * Gets games limited to our own club.
 */
class MatchAPIClient(authKey: String) : BSMAPIClient(authKey) {
    suspend fun loadGamesForClub(
        season: Int?,
        gamedays: String?,
        compact: Boolean? = false
    ): List<Game> {
        val queryParameters = mutableListOf(
            SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
            GAMEDAY_FILTER to (gamedays ?: Gameday.CURRENT.value),
        )

        if (compact == true) {
            queryParameters.add(Pair(COMPACT_FILTER, "true"))
        }

        return apiCall<List<Game>>(
            resource = "clubs/$CLUB_ID/matches.json",
            queryParameters = queryParameters
        ) ?: listOf()
    }

    /**
     * Gets games in general, will also include non-Skylarks games without a search parameter.
     *
     * Example request: https://bsm.baseball-softball.de/matches.json?api_key=REDACTED&search=skylarks&filter%5Bseasons%5D%5B%5D=2024&filter%5Bgamedays%5D%5B%5D=next
     */
    suspend fun loadAllGames(
        season: Int? = DEFAULT_SEASON,
        gamedays: String? = Gameday.ANY.value,
        leagues: Int? = null,
        organizations: Int? = null,
        search: String? = null,
        compact: Boolean? = false,
    ): List<Game> {
        val queryParameters = mutableListOf(
            SEASON_FILTER to (season ?: DEFAULT_SEASON).toString(),
            GAMEDAY_FILTER to (gamedays ?: "current"),
        )

        // Careful: without specifying any additional query, it returns all games for all leagues in Germany
        // => massive JSON response
        if (leagues != null) {
            queryParameters.add(LEAGUE_FILTER to (leagues.toString()))
        }

        if (organizations != null) {
            queryParameters.add(ORGANIZATION_FILTER to "federation_$organizations")
        }

        if (!search.isNullOrEmpty()) {
            queryParameters.add(Pair(TEAM_SEARCH, search))
        }

        if (compact == true) {
            queryParameters.add(Pair(COMPACT_FILTER, "true"))
        }

        return apiCall<List<Game>>(
            resource = "matches.json",
            queryParameters = queryParameters,
        ) ?: listOf()
    }

    suspend fun getBoxScoreForGame(gameID: Int): MatchBoxScore? {
        return apiCall<MatchBoxScore>(
            resource = "matches/$gameID/match_boxscore.json",
        )
    }
}