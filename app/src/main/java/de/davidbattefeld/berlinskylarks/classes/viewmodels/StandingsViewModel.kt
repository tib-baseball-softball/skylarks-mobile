package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.LeagueGroupsAPIRequest
import de.davidbattefeld.berlinskylarks.classes.api.TablesAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import de.davidbattefeld.berlinskylarks.testdata.testTable
import kotlinx.coroutines.launch
import model.LeagueGroup

class StandingsViewModel(application: Application): GenericViewModel(application) {
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var table = mutableStateOf(testTable)

    private val tablesRequest = TablesAPIRequest()
    private val leagueGroupsRequest = LeagueGroupsAPIRequest()

    override fun load() {
        leagueGroups.clear()
        readSelectedSeason()

        viewModelScope.launch {
            leagueGroups.addAll(leagueGroupsRequest.loadLeagueGroupsForClub(selectedSeason))
        }
    }

    fun loadSingleTable(id: Int)  {
        viewState.value = ViewState.Loading

        viewModelScope.launch {
             table.value = tablesRequest.loadSingleTable(id)

            if (table.value.league_id == 9999) {
                viewState.value = ViewState.NoResults
            } else {
                viewState.value = ViewState.Found
            }
        }
    }
}