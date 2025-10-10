package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.Field
import de.berlinskylarks.shared.database.repository.FieldRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = FieldsViewModel.Factory::class)
class FieldsViewModel @AssistedInject constructor(
    fieldRepository: FieldRepository,
    userPreferencesRepository: UserPreferencesRepository,
) : GenericViewModel(userPreferencesRepository) {
    val fields: StateFlow<List<Field>> =
        fieldRepository.getAllFields()
            .map { list -> list.map { it.toField() } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList(),
            )

    @AssistedFactory
    interface Factory {
        fun create(): FieldsViewModel
    }
}