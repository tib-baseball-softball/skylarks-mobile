package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.Functionary
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FunctionaryViewModel @Inject constructor(
    functionaryRepository: FunctionaryRepository,
    userPreferencesRepository: UserPreferencesRepository
) : GenericViewModel(userPreferencesRepository) {
    val functionariesList: StateFlow<List<Functionary>> =
        functionaryRepository.getAllFunctionariesStream()
            .map { dbFunctionaries ->
                dbFunctionaries.map { it.toFunctionary() }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList(),
            )

    init {
        // TODO: this runs on every navigation, offload to WorkManager
        viewModelScope.launch {
            functionaryRepository.syncFunctionaries()
        }
    }
}