package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.TeamsAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import kotlinx.coroutines.launch
import model.SkylarksTeam

class TeamsViewModel(application: Application): GenericViewModel(application) {
    var teams = mutableStateListOf<SkylarksTeam>()

    val request = TeamsAPIRequest()
    override fun load() {
        teams.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            teams.addAll(request.loadAllTeams())

            viewState = if (teams.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }
}