package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.LeagueGroupsAPIRequest
import de.davidbattefeld.berlinskylarks.classes.api.TablesAPIRequest
import kotlinx.coroutines.launch
import model.LeagueTable

class StandingsViewModel(application: Application): GenericViewModel(application) {
    var tables = mutableStateListOf<LeagueTable>()

    private val tablesRequest = TablesAPIRequest()
    private val leagueGroupsRequest = LeagueGroupsAPIRequest()

    override fun load() {
        tables.clear()
        readSelectedSeason()

        viewModelScope.launch {
            val leagueGroups = leagueGroupsRequest.loadLeagueGroupsForClub(selectedSeason)
            leagueGroups.forEach {
                tables.add(tablesRequest.loadSingleTable(it.id))
            }
        }
    }
}