package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LeagueGroupsViewModel(userPreferencesRepository: UserPreferencesRepository) :
    GenericViewModel(userPreferencesRepository) {
    var leagueGroups = mutableStateListOf<LeagueGroup>()

    private val leagueGroupsAPIClient = LeagueGroupsAPIClient(authKey = BSM_API_KEY)

    override fun load() {
        viewState = ViewState.Loading
        leagueGroups.clear()

        viewModelScope.launch {
            val season = userPreferencesFlow.firstOrNull()?.season ?: BSMAPIClient.DEFAULT_SEASON
            leagueGroups.addAll(leagueGroupsAPIClient.loadLeagueGroupsForClub(season))
            viewState = if (leagueGroups.isNotEmpty()) {
                ViewState.Found
            } else {
                ViewState.NoResults
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LeagueGroupsViewModel(
                    userPreferencesRepository = berlinSkylarksApplication().container.userPreferencesRepository
                )
            }
        }
    }
}