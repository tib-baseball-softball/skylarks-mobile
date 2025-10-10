package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.Field
import de.berlinskylarks.shared.database.repository.FieldRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.FieldDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = FieldDetailViewModel.Factory::class)
class FieldDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: FieldDetail,
    userPreferencesRepository: UserPreferencesRepository,
    fieldRepository: FieldRepository,
) : GenericViewModel(userPreferencesRepository) {
    val field: StateFlow<Field?> = fieldRepository.getFieldByID(navKey.id)
        .map { it?.toField() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: FieldDetail): FieldDetailViewModel
    }
}