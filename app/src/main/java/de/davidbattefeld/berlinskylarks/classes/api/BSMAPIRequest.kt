package de.davidbattefeld.berlinskylarks.classes.api

import android.icu.util.Calendar
import de.davidbattefeld.berlinskylarks.classes.api.APIClient.client
import de.davidbattefeld.berlinskylarks.global.API_KEY
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

abstract class BSMAPIRequest {

    companion object {
        const val CLUB_ID = 485
        // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
        const val BSVBB_ORGANIZATION_ID = 9
        const val DBV_ORGANIZATION_ID = 1

        const val API_URL = "https://bsm.baseball-softball.de"

        const val SEASON_FILTER = "filters[seasons][]"
        const val GAMEDAY_FILTER = "filters[gamedays][]"
        const val ORGANIZATION_FILTER = "filters[organizations][]"
        const val TEAM_SEARCH = "search"

        val DEFAULT_SEASON = Calendar.getInstance().get(Calendar.YEAR)
    }

    @OptIn(ExperimentalSerializationApi::class)
    protected val jsonBuilder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    /**
     * Generic API call method. queryParameters is not a Map because duplicate query parameters
     * need to be possible.
     */
    protected suspend inline fun <reified T> apiCall(
        resource: String,
        queryParameters: MutableList<Pair<String, String>> = mutableListOf()
    ): T {
        var result: T
        withContext(Dispatchers.IO) {
            val response = client.get(API_URL) {
                url {
                    appendPathSegments(resource)
                    queryParameters.forEach {
                        parameters.append(it.first, it.second)
                    }
                    parameters.append("api_key", API_KEY)
                }
            }
            result = jsonBuilder.decodeFromString<T>(response.body())
        }

        return result
    }
}