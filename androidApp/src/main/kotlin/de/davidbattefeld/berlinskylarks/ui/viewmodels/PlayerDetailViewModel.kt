package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.Player
import de.berlinskylarks.shared.database.repository.PlayerRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.PlayerDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = PlayerDetailViewModel.Factory::class)
class PlayerDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: PlayerDetail,
    userPreferencesRepository: UserPreferencesRepository,
    playerRepository: PlayerRepository,
) : GenericViewModel(userPreferencesRepository) {
    var player: StateFlow<Player?> = playerRepository.getPlayerByID(navKey.id)
        .map { dbPlayer ->
            dbPlayer?.toPlayer()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: PlayerDetail): PlayerDetailViewModel
    }
}