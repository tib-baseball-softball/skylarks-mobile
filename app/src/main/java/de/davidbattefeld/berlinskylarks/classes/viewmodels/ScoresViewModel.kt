package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.LeagueGroupsAPIRequest
import de.davidbattefeld.berlinskylarks.classes.api.MatchAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup
import kotlinx.coroutines.launch
import model.Game
import model.LeagueGroup

class ScoresViewModel(application: Application) : GenericViewModel(application) {
    var games = mutableStateListOf<Game>()
    var skylarksGames = mutableStateListOf<Game>()
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var filteredLeagueGroup by mutableStateOf(testLeagueGroup)

    var tabState by mutableIntStateOf(1)

    private val matchAPIRequest = MatchAPIRequest()
    private val leagueGroupsAPIRequest = LeagueGroupsAPIRequest()

    override fun load() {
        leagueGroups.clear()

        viewModelScope.launch {
            readSelectedSeason()
            viewState = ViewState.Loading

            leagueGroups.addAll(leagueGroupsAPIRequest.loadLeagueGroupsForClub(selectedSeason))
            loadGames()

            viewState = if (games.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }

    fun onLeagueFilterChanged(leagueGroup: LeagueGroup) {
        filteredLeagueGroup = leagueGroup
        viewModelScope.launch {
            loadGames()
        }
    }

    private suspend fun loadGames() {
        games.clear()
        skylarksGames.clear()

        val gamedays = when (tabState) {
            0 -> "previous"
            1 -> "current"
            2 -> "next"
            3 -> "any"
            else -> { throw Exception("tabState has invalid value that cannot be used to determine Gameday") }
        }

        if (filteredLeagueGroup.id == BOGUS_ID) {
            games.addAll(matchAPIRequest.loadGamesForClub(selectedSeason, gamedays))
        } else {
            games.addAll(matchAPIRequest.loadAllGames(
                season = selectedSeason,
                gamedays = gamedays,
                leagues = filteredLeagueGroup.id
            ))
        }
        games.forEach {
            it.addDate()
            it.determineGameStatus()
            it.setCorrectLogos()
        }
        skylarksGames.addAll(games.filter {
            it.away_team_name.contains("Skylarks", ignoreCase = true) ||
                    it.home_team_name.contains("Skylarks", ignoreCase = true)
        })
    }

    fun buildMapsURL(id: Int): String {
        val baseURL = "https://www.google.com/maps/search/"
        val builder = Uri.parse(baseURL).buildUpon()

        val game = games.firstOrNull { it.id == id }

        builder.appendQueryParameter("api", "1")
        builder.appendQueryParameter("map_action", "map")
        builder.appendQueryParameter("query_place_id", game?.field?.name)
        builder.appendQueryParameter("query", "${game?.field?.latitude}, ${game?.field?.longitude}")

        return builder.build().toString()
    }
}