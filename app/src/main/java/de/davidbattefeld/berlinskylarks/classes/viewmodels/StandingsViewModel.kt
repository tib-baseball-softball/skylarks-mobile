package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.LeagueGroupsAPIRequest
import de.davidbattefeld.berlinskylarks.classes.api.TablesAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.testdata.testTable
import kotlinx.coroutines.launch
import model.LeagueGroup

class StandingsViewModel(application: Application) : GenericViewModel(application) {
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var table = mutableStateOf(testTable)

    private val tablesRequest = TablesAPIRequest()
    private val leagueGroupsRequest = LeagueGroupsAPIRequest()

    override fun load() {
        viewState = ViewState.Loading
        leagueGroups.clear()
        readSelectedSeason()

        viewModelScope.launch {
            leagueGroups.addAll(leagueGroupsRequest.loadLeagueGroupsForClub(selectedSeason.intValue))
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
            table.value = tablesRequest.loadSingleTable(id)

            viewState = if (table.value.league_id == BOGUS_ID) ViewState.NoResults else ViewState.Found
        }
    }
}