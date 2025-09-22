package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.api.TeamsAPIClient
import de.berlinskylarks.shared.data.model.SkylarksTeam
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.AUTH_HEADER
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

class TeamsViewModel(userPreferencesRepository: UserPreferencesRepository) :
    GenericViewModel(userPreferencesRepository) {
    var teams = mutableStateListOf<SkylarksTeam>()

    private val client = TeamsAPIClient(authKey = AUTH_HEADER)
    override fun load() {
        teams.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            teams.addAll(client.loadAllTeams())

            viewState = if (teams.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }
}