package de.davidbattefeld.berlinskylarks.ui.viewmodels

import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository

@HiltViewModel(assistedFactory = LegalNoticeViewModel.Factory::class)
class LegalNoticeViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository
) : GenericViewModel(userPreferencesRepository) {

    @AssistedFactory
    interface Factory {
        fun create(): LegalNoticeViewModel
    }
}
