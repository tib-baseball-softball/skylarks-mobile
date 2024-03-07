package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.api.BSMAPIRequest
import de.davidbattefeld.berlinskylarks.enums.ViewState
import de.davidbattefeld.berlinskylarks.global.readInt
import de.davidbattefeld.berlinskylarks.global.writeInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.JSONDataObject

abstract class GenericViewModel(application: Application) : AndroidViewModel(application), ViewModelInterface {
    var selectedSeason by mutableIntStateOf(BSMAPIRequest.DEFAULT_SEASON)

    var viewState by mutableStateOf(ViewState.NotInitialised)

    init {
        readSelectedSeason()

        // extra check since apparently using the current year as fallback didn't work
        if (selectedSeason == 0) {
            selectedSeason = BSMAPIRequest.DEFAULT_SEASON
            writeSelectedSeason(selectedSeason)
        }
    }

    fun readSelectedSeason() {
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch(Dispatchers.IO) {
            val savedSeason = context.readInt("season").first()

            // this may look weird, but is needed because of Android State management
            withContext(Dispatchers.Main) {
                selectedSeason = savedSeason
            }
        }
    }
    fun writeSelectedSeason(season: Int) {
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch(Dispatchers.IO) {
            context.writeInt("season", season)
        }
    }

    fun <T: JSONDataObject> getFiltered(id: Int, list: List<T>): T? {
        return list.firstOrNull { it.id == id }
    }
}