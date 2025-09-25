package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.api.TeamsAPIClient
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val client: TeamsAPIClient,
    userPreferencesRepository: UserPreferencesRepository
) : GenericViewModel(userPreferencesRepository) {
    var teams = mutableStateListOf<SkylarksTeam>()

    override fun load() {
        teams.clear()

        viewModelScope.launch {
            viewState = ViewState.Loading
            teams.addAll(client.loadAllTeams())

            viewState = if (teams.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }
}