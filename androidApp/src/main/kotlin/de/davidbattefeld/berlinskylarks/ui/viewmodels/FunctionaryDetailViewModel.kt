package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.Functionary
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.FunctionaryDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = FunctionaryDetailViewModel.Factory::class)
class FunctionaryDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: FunctionaryDetail,
    userPreferencesRepository: UserPreferencesRepository,
    functionaryRepository: FunctionaryRepository,
) : GenericViewModel(userPreferencesRepository) {
    val functionary: StateFlow<Functionary?> = functionaryRepository.getFunctionaryByID(navKey.id)
        .map { it?.toFunctionary() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: FunctionaryDetail): FunctionaryDetailViewModel
    }
}