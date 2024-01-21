package de.davidbattefeld.berlinskylarks.classes

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import model.GameScore

class ScoresViewModel(application: Application) : GenericViewModel(application) {
    var gameScores = mutableStateListOf<GameScore>()
    var tabState by mutableIntStateOf(1)

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")

    override fun load() {
        gameScores.clear()
        readSelectedSeason()

        val gamedays = when (tabState) {
            0 -> "previous"
            1 -> "current"
            2 -> "next"
            3 -> "any"
            else -> { throw Exception("tabState has invalid value that cannot be used to determine Gameday") }
        }

        gameScores.addAll(api.loadGamesForClub(selectedSeason, gamedays))
        gameScores.forEach {
            it.addDate()
            it.determineGameStatus()
            it.setCorrectLogos()
        }
    }
}