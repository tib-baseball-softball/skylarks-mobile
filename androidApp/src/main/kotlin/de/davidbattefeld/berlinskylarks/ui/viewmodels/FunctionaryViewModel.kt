package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FunctionaryViewModel @Inject constructor(
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