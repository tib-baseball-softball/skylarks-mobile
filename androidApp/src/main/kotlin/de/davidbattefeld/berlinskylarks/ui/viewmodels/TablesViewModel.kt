package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.ui.nav.StandingsDetail
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

class TablesViewModel(
    private val tablesAPIClient: TablesAPIClient,
    val key: StandingsDetail,
    userPreferencesRepository: UserPreferencesRepository
) : GenericViewModel(userPreferencesRepository) {
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
            return TablesViewModel(
                tablesAPIClient = extras.berlinSkylarksApplication().container.tablesAPIClient,
                key = key,
                userPreferencesRepository = extras.berlinSkylarksApplication().container.userPreferencesRepository
            ) as T
        }
    }
}