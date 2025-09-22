package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import kotlinx.coroutines.launch

class FunctionaryViewModel(
    functionaryRepository: FunctionaryRepository,
    userPreferencesRepository: UserPreferencesRepository
) : GenericViewModel(userPreferencesRepository) {
    var functionariesList = functionaryRepository.getAllFunctionariesStream()

    init {
        // TODO: this runs on every navigation, offload to WorkManager
        viewModelScope.launch {
            functionaryRepository.syncFunctionaries()
        }
    }
}