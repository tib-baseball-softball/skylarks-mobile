package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.TeamsAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import kotlinx.coroutines.launch
import model.Player
import model.SkylarksTeam

class TeamsViewModel(application: Application): GenericViewModel(application) {
    var teams = mutableStateListOf<SkylarksTeam>()
    var players = mutableStateListOf<Player>()

    val request = TeamsAPIRequest()
    override fun load() {
        teams.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            teams.addAll(request.loadAllTeams())

            viewState = if (teams.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }

    fun loadPlayers(teamID: Int) {
        players.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            players.addAll(request.loadPlayersForTeam(teamID))

            viewState = if (players.isNotEmpty() || teams.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }
}