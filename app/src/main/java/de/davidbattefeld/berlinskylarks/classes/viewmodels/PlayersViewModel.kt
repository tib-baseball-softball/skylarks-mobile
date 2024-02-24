package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.TeamsAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import kotlinx.coroutines.launch
import model.Player

class PlayersViewModel(application: Application): GenericViewModel(application) {
    var players = mutableStateListOf<Player>()

    val request = TeamsAPIRequest()

    fun loadPlayers(teamID: Int) {
        players.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            players.addAll(request.loadPlayersForTeam(teamID))

            viewState = if (players.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }
}