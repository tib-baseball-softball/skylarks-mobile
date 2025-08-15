package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class StandingsViewModel(application: Application) : GenericViewModel(application) {
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var table = mutableStateOf<LeagueTable?>(null)

    private val tablesAPIClient = TablesAPIClient(authKey = BSM_API_KEY)
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

    fun loadSingleTable(id: Int) {
        viewState = ViewState.Loading

        viewModelScope.launch {
            table.value = tablesAPIClient.loadSingleTable(id)

            viewState = if (table.value?.league_id == BOGUS_ID) ViewState.NoResults else ViewState.Found
        }
    }
}