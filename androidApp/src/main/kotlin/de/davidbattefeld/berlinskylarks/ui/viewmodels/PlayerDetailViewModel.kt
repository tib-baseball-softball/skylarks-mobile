package de.davidbattefeld.berlinskylarks.ui.viewmodels

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.PlayerDetail

@HiltViewModel(assistedFactory = PlayerDetailViewModel.Factory::class)
class PlayerDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: PlayerDetail,
    userPreferencesRepository: UserPreferencesRepository
): GenericViewModel(userPreferencesRepository) {
    @AssistedFactory
    interface Factory {
        fun create(navKey: PlayerDetail): PlayerDetailViewModel
    }
}