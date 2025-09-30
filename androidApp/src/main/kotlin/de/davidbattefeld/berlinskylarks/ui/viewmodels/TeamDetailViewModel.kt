package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.Player
import de.berlinskylarks.shared.database.repository.PlayerRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.TeamDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = TeamDetailViewModel.Factory::class)
class TeamDetailViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    playerRepository: PlayerRepository,
    @Assisted val navKey: TeamDetail,
) : GenericViewModel(userPreferencesRepository) {
    var players: StateFlow<List<Player>> = playerRepository.getPlayersForTeam(navKey.id)
        .map { dbPlayers ->
            dbPlayers.map { it.toPlayer() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList(),
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: TeamDetail): TeamDetailViewModel
    }
}