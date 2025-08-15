package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.model.JSONDataObject
import de.davidbattefeld.berlinskylarks.data.preferences.dataStore
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

abstract class GenericViewModel(application: Application) : AndroidViewModel(application), ViewModelInterface {
    // this should use DI instead
    private val userPreferencesRepository = UserPreferencesRepository(application.dataStore)
    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    var viewState by mutableStateOf(ViewState.NotInitialised)

    fun updateSelectedSeason(season: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateSelectedSeason(season)
        }
    }

    fun <T: JSONDataObject> getFiltered(id: Int, list: List<T>): T? {
        return list.firstOrNull { it.id == id }
    }
}