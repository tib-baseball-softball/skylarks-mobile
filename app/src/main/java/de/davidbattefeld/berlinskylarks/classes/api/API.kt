package de.davidbattefeld.berlinskylarks.classes.api

import android.icu.util.Calendar
import de.davidbattefeld.berlinskylarks.global.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import model.GameScore
import java.net.URL

open class API {
    protected val API_URL = "https://bsm.baseball-softball.de"

    val clubID = 485

    protected val DEFAULT_SEASON = Calendar.getInstance().get(Calendar.YEAR)

    // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
    val organizationID = 9

    @OptIn(ExperimentalSerializationApi::class)
    protected val jsonBuilder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    protected suspend inline fun <reified T> apiCall(
        resource: String,
        season: Int?,
        filters: String?,
        search: String?,
    ): T {
        val url = buildURL(season, search, resource, filters)

        var result: T

        var json: String
        withContext(Dispatchers.IO) {
            json = fetchJSONData(url = url)
            result = jsonBuilder.decodeFromString<T>(json)
        }

        return result
    }

    protected fun buildURL(
        season: Int?,
        search: String?,
        resource: String,
        filters: String?,
    ): String {
        val currentSeason = season ?: DEFAULT_SEASON

        var searchTerm = ""
        if (!search.isNullOrEmpty()) {
            searchTerm = "&search=$search"
        }

        return "$API_URL/${resource}.json?filters[seasons][]=$currentSeason$searchTerm$filters&api_key=${API_KEY}"
    }

    // reads URL and returns the full response as JSON
    protected fun fetchJSONData(url: String): String {
        return URL(url).readText()
    }

    suspend fun loadGamesForClub(
        season: Int?,
        gamedays: String?,
        showExternal: Boolean = false,
    ): List<GameScore> {

        // Gameday Filter
        var gamedayParam: String
        if (gamedays.isNullOrEmpty()) {
            gamedayParam = "&filters[gamedays][]=current"
        } else {
            gamedayParam = "&filters[gamedays][]=$gamedays"
        }

        return apiCall<List<GameScore>>(
            resource = "matches",
            season = season,
            filters = gamedayParam,
            search = "skylarks",
        )
    }
}