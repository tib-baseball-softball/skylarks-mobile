package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.Player
import de.berlinskylarks.shared.database.repository.PlayerRepository
import de.berlinskylarks.shared.database.repository.TiBTeamRepository
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
    teamRepository: TiBTeamRepository,
    @Assisted val navKey: TeamDetail,
) : GenericViewModel(userPreferencesRepository) {
    val team = teamRepository.getTeamByID(navKey.id)
        .map { tiBTeamEntity ->
            tiBTeamEntity?.toTeam()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )


    val players: StateFlow<List<Player>> = playerRepository.getPlayersForTeam(navKey.id)
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