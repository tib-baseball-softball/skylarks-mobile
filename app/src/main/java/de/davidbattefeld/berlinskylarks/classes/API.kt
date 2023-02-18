package de.davidbattefeld.berlinskylarks.classes

import android.icu.util.Calendar
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import de.davidbattefeld.berlinskylarks.global.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import model.GameScore
import java.lang.reflect.Type
import java.net.URL

class API {
    private val API_URL = "https://bsm.baseball-softball.de"

    val clubID = 485

    val DEFAULT_SEASON = Calendar.getInstance().get(Calendar.YEAR)

    // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
    val organizationID = 9

    // builds the actual URL and returns JSON contents
    private inline fun <reified T> apiCall(
        resource: String,
        season: Int?,
        filters: String?, //WIP
        typeToken: Type
    ): T {
        val url = "${API_URL}/${resource}.json?api_key=${API_KEY}"

        var result: T
        runBlocking {
            val json = fetchJSONData(url = url)
            withContext(Dispatchers.IO) { json }
            val gson = GsonBuilder().create()
            result = gson.fromJson<T>(json, typeToken)
        }
        return result
    }

    // reads URL and returns the full response as JSON
    private fun fetchJSONData(url: String): String {
        return URL(url).readText()
    }

    fun loadGamesForClub(
        season: Int?,
        gamedays: String?,
        showExternal: Boolean = false
    ): List<GameScore> {
        val type = object : TypeToken<List<GameScore>>() {}.type

        // Gameday Filter
        var gamedayParam: String
        if (gamedays.isNullOrEmpty()) {
            gamedayParam = "filters[gamedays][]=current"
        } else {
            gamedayParam = "filters[gamedays][]=$gamedays"
        }

        return apiCall(resource = "clubs/${clubID}/matches", season = season, filters = null, typeToken = type)
    }
}