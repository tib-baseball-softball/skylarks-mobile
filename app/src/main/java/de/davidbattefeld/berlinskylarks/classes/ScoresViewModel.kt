package de.davidbattefeld.berlinskylarks.classes

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.google.gson.reflect.TypeToken
import de.davidbattefeld.berlinskylarks.global.API_KEY
import de.davidbattefeld.berlinskylarks.global.readInt
import de.davidbattefeld.berlinskylarks.global.writeInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.GameScore

class ScoresViewModel(application: Application) : ContentLoader(application) {
    var gameScores = mutableStateListOf<GameScore>()
    var gamesCount by mutableStateOf(0)

    var selectedSeason by mutableStateOf(
        2021
        //Calendar.getInstance().get(Calendar.YEAR)
    )

    override var url = "https://bsm.baseball-softball.de/matches.json?filters[seasons][]=2022&search=skylarks&filters[gamedays][]=any&api_key=$API_KEY"

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")

    fun loadGames() = runBlocking {
        launch {
            var json: String
            val type = object : TypeToken<List<GameScore>>() {}.type
            withContext(Dispatchers.IO) { json = fetchJSONData() }
            val result: List<GameScore> = parseResponse<List<GameScore>>(json = json, typeToken = type)
            gameScores.addAll(result)
            gamesCount = gameScores.count()
            //teeeeeest()
        }
    }

    fun teeeeeest() {
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch(Dispatchers.IO) {
            context.writeInt("season", 2000)
            val temp = context.readInt("season").first()
            selectedSeason = temp
        }
    }
}