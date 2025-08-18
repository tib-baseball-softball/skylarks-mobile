package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

class TablesViewModel(application: Application) : GenericViewModel(application) {
    var table = mutableStateOf<LeagueTable?>(null)

    private val tablesAPIClient = TablesAPIClient(authKey = BSM_API_KEY)

    override fun load() {}

    fun loadSingleTable(id: Int) {
        viewState = ViewState.Loading

        viewModelScope.launch {
            table.value = tablesAPIClient.loadSingleTable(id)

            viewState = if (table.value?.league_id == BOGUS_ID) ViewState.NoResults else ViewState.Found
        }
    }
}