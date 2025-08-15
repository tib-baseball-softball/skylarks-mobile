package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.data.api.TeamsAPIClient
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch
import de.davidbattefeld.berlinskylarks.data.model.SkylarksTeam

class TeamsViewModel(application: Application): GenericViewModel(application) {
    var teams = mutableStateListOf<SkylarksTeam>()

    private val client = TeamsAPIClient()
    override fun load() {
        teams.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            teams.addAll(client.loadAllTeams())

            viewState = if (teams.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }
}