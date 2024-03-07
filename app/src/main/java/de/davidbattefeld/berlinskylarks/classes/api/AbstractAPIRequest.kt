package de.davidbattefeld.berlinskylarks.classes.api

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

abstract class AbstractAPIRequest {
    abstract val API_URL: String
    abstract val authKey: String

    @OptIn(ExperimentalSerializationApi::class)
    protected val jsonBuilder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    abstract fun URLBuilder.addAuthorizationParameters()

    abstract fun HttpRequestBuilder.addRequestHeaders()

    /**
     * Generic API call method. queryParameters is not a Map because duplicate query parameters
     * need to be possible.
     */
    protected suspend inline fun <reified T> apiCall(
        resource: String,
        queryParameters: MutableList<Pair<String, String>> = mutableListOf()
    ): T? {
        var result: T? = null
        withContext(Dispatchers.IO) {
            val response = APIClient.client.get(API_URL) {
                url {
                    appendPathSegments(resource)
                    queryParameters.forEach {
                        parameters.append(it.first, it.second)
                    }
                    addAuthorizationParameters()
                }
                addRequestHeaders()
                println(url)
            }
            try {
                result = jsonBuilder.decodeFromString<T>(response.body())
            } catch (e: SerializationException) {
                print(e.message)
            } catch (e: IllegalArgumentException) {
                print(e.message)
            }
        }
        return result
    }
}