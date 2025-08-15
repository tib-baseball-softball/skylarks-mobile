package de.berlinskylarks.shared.data.api

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

abstract class AbstractAPIClient {
    protected abstract val API_URL: String
    protected abstract val authKey: String

    protected val jsonBuilder = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    protected abstract fun URLBuilder.addAuthorizationParameters()

    protected abstract fun HttpRequestBuilder.addRequestHeaders()

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
            try {
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
                result = jsonBuilder.decodeFromString<T>(response.body())
            } catch (e: Exception) {
                println(e.message)
                e.printStackTrace()
            }
        }
        return result
    }
}