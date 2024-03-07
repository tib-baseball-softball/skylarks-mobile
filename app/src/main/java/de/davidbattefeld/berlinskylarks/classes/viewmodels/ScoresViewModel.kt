package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.MatchAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import kotlinx.coroutines.launch
import model.Game

class ScoresViewModel(application: Application) : GenericViewModel(application) {
    var games = mutableStateListOf<Game>()
    var skylarksGames = mutableStateListOf<Game>()

    var tabState by mutableIntStateOf(1)

    private val request = MatchAPIRequest()

    override fun load() {
        games.clear()
        skylarksGames.clear()
        readSelectedSeason()

        val gamedays = when (tabState) {
            0 -> "previous"
            1 -> "current"
            2 -> "next"
            3 -> "any"
            else -> { throw Exception("tabState has invalid value that cannot be used to determine Gameday") }
        }

        viewModelScope.launch {
            viewState = ViewState.Loading
            games.addAll(request.loadGamesForClub(selectedSeason.intValue, gamedays))
            games.forEach {
                it.addDate()
                it.determineGameStatus()
                it.setCorrectLogos()
            }
            skylarksGames.addAll(games.filter {
                it.away_team_name.contains("Skylarks", ignoreCase = true) ||
                it.home_team_name.contains("Skylarks", ignoreCase = true)
            })

            viewState = if (games.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
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