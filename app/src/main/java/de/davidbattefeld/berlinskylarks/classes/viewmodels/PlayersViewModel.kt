package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.TeamsAPIClient
import de.davidbattefeld.berlinskylarks.enums.ViewState
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.model.Player
import kotlinx.coroutines.launch

class PlayersViewModel(application: Application) : GenericViewModel(application) {
    var players = mutableStateListOf<Player>()
    var lastLoadedTeam by mutableIntStateOf(BOGUS_ID)

    private val client = TeamsAPIClient()

    fun loadPlayers(teamID: Int) {
        players.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            players.addAll(client.loadPlayersForTeam(teamID))

            viewState = if (players.isNotEmpty()) ViewState.Found else ViewState.NoResults

            lastLoadedTeam = teamID
        }
    }
}