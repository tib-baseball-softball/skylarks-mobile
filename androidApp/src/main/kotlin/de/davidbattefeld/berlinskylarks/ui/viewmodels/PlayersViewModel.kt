package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.api.TeamsAPIClient
import de.berlinskylarks.shared.data.model.Player
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.AUTH_HEADER
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

class PlayersViewModel(userPreferencesRepository: UserPreferencesRepository) :
    GenericViewModel(userPreferencesRepository) {
    var players = mutableStateListOf<Player>()
    var lastLoadedTeam by mutableIntStateOf(BOGUS_ID)

    private val client = TeamsAPIClient(authKey = AUTH_HEADER)

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