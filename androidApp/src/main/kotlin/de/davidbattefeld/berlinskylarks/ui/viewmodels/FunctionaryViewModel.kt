package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FunctionaryViewModel(
    application: Application,
    functionaryRepository: FunctionaryRepository
) : GenericViewModel(application = application) {
    lateinit var functionariesList: Flow<List<FunctionaryEntity>>

    init {
        viewModelScope.launch {
            functionariesList = functionaryRepository.getAllFunctionariesStream()
        }
    }
}