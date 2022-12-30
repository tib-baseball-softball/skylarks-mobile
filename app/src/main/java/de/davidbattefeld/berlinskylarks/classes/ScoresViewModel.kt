package de.davidbattefeld.berlinskylarks.classes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.gson.reflect.TypeToken
import de.davidbattefeld.berlinskylarks.global.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.GameScore

class ScoresViewModel : ContentLoader() {
    var gamescores = mutableStateListOf<GameScore>()
    var gamesCount by mutableStateOf(0)
    override var url = "https://bsm.baseball-softball.de/matches.json?filters[seasons][]=2022&search=skylarks&filters[gamedays][]=any&api_key=$API_KEY"

    val options = listOf("Option 1", "Option 2", "Option 3", "Option 4", "Option 5")

    fun loadGames() = runBlocking {
        launch {
            var json: String
            val type = object : TypeToken<List<GameScore>>() {}.type
            withContext(Dispatchers.IO) { json = fetchJSONData() }
            val result: List<GameScore> = parseResponse<List<GameScore>>(json = json, typeToken = type)
            gamescores.addAll(result)
            gamesCount = gamescores.count()
            println(gamesCount)
            //println(gamescores[0])
        }
    }
}