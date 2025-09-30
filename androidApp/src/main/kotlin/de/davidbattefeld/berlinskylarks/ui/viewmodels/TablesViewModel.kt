package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.LeagueTable
import de.berlinskylarks.shared.database.repository.LeagueGroupRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.StandingsDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = TablesViewModel.Factory::class)
class TablesViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    leagueGroupRepository: LeagueGroupRepository,
    @Assisted val navKey: StandingsDetail,
) : GenericViewModel(userPreferencesRepository) {
    var table: StateFlow<LeagueTable?> = leagueGroupRepository.getLeagueGroupByID(navKey.id)
        .map { dbLeagueGroup ->
            dbLeagueGroup?.table?.json
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: StandingsDetail): TablesViewModel
    }
}