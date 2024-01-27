package de.davidbattefeld.berlinskylarks.classes.api

import android.icu.util.Calendar
import de.davidbattefeld.berlinskylarks.global.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import java.net.URL

abstract class BSMAPIRequest {

    companion object {
        const val CLUB_ID = 485
        // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
        const val ORGANIZATION_ID = 9

        const val API_URL = "https://bsm.baseball-softball.de"
    }
    protected val DEFAULT_SEASON = Calendar.getInstance().get(Calendar.YEAR)

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
}