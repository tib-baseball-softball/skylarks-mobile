package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.MatchAPIRequest
import kotlinx.coroutines.launch
import model.Game

class ScoresViewModel(application: Application) : GenericViewModel(application) {
    var games = mutableStateListOf<Game>()
    var tabState by mutableIntStateOf(1)

    private val request = MatchAPIRequest()

    override fun load() {
        games.clear()
        readSelectedSeason()

        val gamedays = when (tabState) {
            0 -> "previous"
            1 -> "current"
            2 -> "next"
            3 -> "any"
            else -> { throw Exception("tabState has invalid value that cannot be used to determine Gameday") }
        }

        viewModelScope.launch {
            games.addAll(request.loadGamesForClub(selectedSeason, gamedays))
            games.forEach {
                it.addDate()
                it.determineGameStatus()
                it.setCorrectLogos()
            }
        }
    }

    fun getFilteredGame(id: Int): Game? {
        return games.firstOrNull { it.id == id }
    }
}