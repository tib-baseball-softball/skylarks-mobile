package de.davidbattefeld.berlinskylarks.classes

import android.app.Application
import android.icu.util.Calendar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.global.readInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    val possibleSeasons = (2021..Calendar.getInstance().get(Calendar.YEAR)).toList()
    var selectedSeason by mutableStateOf(Calendar.getInstance().get(Calendar.YEAR))

    //TODO: call
    fun readCorrectSeason() {
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch(Dispatchers.IO) {
            selectedSeason = context.readInt("season").first()
        }
    }
}