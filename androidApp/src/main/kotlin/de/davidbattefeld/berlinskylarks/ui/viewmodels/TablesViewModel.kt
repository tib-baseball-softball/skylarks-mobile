package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.data.model.LeagueTable
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.ui.nav.StandingsDetail
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = TablesViewModel.Factory::class)
class TablesViewModel @AssistedInject constructor(
    private val tablesAPIClient: TablesAPIClient,
    userPreferencesRepository: UserPreferencesRepository,
    @Assisted val navKey: StandingsDetail,
) : GenericViewModel(userPreferencesRepository) {
    var table = mutableStateOf<LeagueTable?>(null)

    fun loadSingleTable(id: Int) {
        viewState = ViewState.Loading

        viewModelScope.launch {
            table.value = tablesAPIClient.loadSingleTable(id)

            viewState =
                if (table.value?.league_id == BOGUS_ID) ViewState.NoResults else ViewState.Found
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: StandingsDetail): TablesViewModel
    }
}