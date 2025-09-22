package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.model.JSONDataObject
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.launch

abstract class GenericViewModel(
    val userPreferencesRepository: UserPreferencesRepository
) : ViewModel(), ViewModelInterface {
    val userPreferencesFlow = userPreferencesRepository.userPreferencesFlow

    var viewState by mutableStateOf(ViewState.NotInitialised)

    fun updateSelectedSeason(season: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateSelectedSeason(season)
        }
    }

    fun <T : JSONDataObject> getFiltered(id: Int, list: List<T>): T? {
        return list.firstOrNull { it.id == id }
    }
}