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
        const val ORGANIZATION_ID = 9

        const val API_URL = "https://bsm.baseball-softball.de"

        const val SEASON_FILTER = "filters[seasons][]"
        const val GAMEDAY_FILTER = "filters[gamedays][]"

        val DEFAULT_SEASON = Calendar.getInstance().get(Calendar.YEAR)
    }

    @OptIn(ExperimentalSerializationApi::class)
    protected val jsonBuilder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    protected suspend inline fun <reified T> apiCall(
        resource: String,
        queryParameters: Map<String, String>? = null
    ): T {
        var result: T
        withContext(Dispatchers.IO) {
            val response = client.get(API_URL) {
                url {
                    appendPathSegments(resource)
                    queryParameters?.forEach {
                        parameters.append(it.key, it.value)
                    }
                    parameters.append("api_key", API_KEY)
                }
            }
            result = jsonBuilder.decodeFromString<T>(response.body())
        }

        return result
    }
}