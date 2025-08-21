package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.ui.nav.StandingsDetail
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

class TablesViewModel(
    application: Application,
    private val tablesAPIClient: TablesAPIClient,
    val key: StandingsDetail,
) : GenericViewModel(application) {
    var table = mutableStateOf<LeagueTable?>(null)

    override fun load() {}

    fun loadSingleTable(id: Int) {
        viewState = ViewState.Loading

        viewModelScope.launch {
            table.value = tablesAPIClient.loadSingleTable(id)

            viewState =
                if (table.value?.league_id == BOGUS_ID) ViewState.NoResults else ViewState.Found
        }
    }

    class Factory(
        private val key: StandingsDetail
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            modelClass: Class<T>,
            extras: CreationExtras
        ): T {
            val application = extras.berlinSkylarksApplication()
            return TablesViewModel(
                application = application,
                tablesAPIClient = extras.berlinSkylarksApplication().container.tablesAPIClient,
                key = key
            ) as T
        }
    }
}