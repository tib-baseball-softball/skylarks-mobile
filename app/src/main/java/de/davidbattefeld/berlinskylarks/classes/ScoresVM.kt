package de.davidbattefeld.berlinskylarks.classes

import de.davidbattefeld.berlinskylarks.global.apiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.GameScore

class ScoresVM : ContentLoader() {
    var gamescores = mutableListOf<GameScore>()
    var gamesCount = 0
    override var url = "https://bsm.baseball-softball.de/matches.json?filters[seasons][]=2022&search=skylarks&filters[gamedays][]=any&api_key=$apiKey"

    fun loadGames() = runBlocking {
        launch {
            withContext(Dispatchers.IO) { gamescores = fetchBSMData(mutableListOf<GameScore>()) }
            gamesCount = gamescores.count()
            println(gamesCount)
            //println(gamescores[0])
        }
    }
}