package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.data.api.BSMAPIClient
import de.davidbattefeld.berlinskylarks.data.api.LeagueGroupsAPIClient
import de.davidbattefeld.berlinskylarks.data.api.TablesAPIClient
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.testdata.testTable
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import de.davidbattefeld.berlinskylarks.data.model.LeagueGroup

class StandingsViewModel(application: Application) : GenericViewModel(application) {
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var table = mutableStateOf(testTable)

    private val tablesAPIClient = TablesAPIClient()
    private val leagueGroupsAPIClient = LeagueGroupsAPIClient()

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

            viewState = if (table.value.league_id == BOGUS_ID) ViewState.NoResults else ViewState.Found
        }
    }
}