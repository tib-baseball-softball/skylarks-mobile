package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun readStaticMarkdownFile(fileName: String, context: Context): String {
        val assetManager = context.assets
        val src = assetManager.open(fileName).bufferedReader().use { it.readText() }
        return src
    }
}