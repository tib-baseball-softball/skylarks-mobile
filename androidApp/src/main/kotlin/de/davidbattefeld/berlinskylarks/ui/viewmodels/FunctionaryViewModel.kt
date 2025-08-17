package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import kotlinx.coroutines.launch

class FunctionaryViewModel(
    application: Application,
    functionaryRepository: FunctionaryRepository
) : GenericViewModel(application = application) {
    var functionariesList = functionaryRepository.getAllFunctionariesStream()

    init {
        // TODO: this runs on every navigation, offload to WorkManager
        viewModelScope.launch {
            functionaryRepository.syncFunctionaries()
        }
    }
}