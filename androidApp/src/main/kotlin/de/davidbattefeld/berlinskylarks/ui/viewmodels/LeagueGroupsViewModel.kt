package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.database.repository.LeagueGroupRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = LeagueGroupsViewModel.Factory::class)
class LeagueGroupsViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    leagueGroupRepository: LeagueGroupRepository,
) : GenericViewModel(userPreferencesRepository) {
    var leagueGroups: StateFlow<List<LeagueGroup>> = leagueGroupRepository.getAllLeagueGroups()
        .map { dbLeagueGroups ->
            dbLeagueGroups.map { it.toLeagueGroup() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList(),
        )

    @AssistedFactory
    interface Factory {
        fun create(): LeagueGroupsViewModel
    }
}