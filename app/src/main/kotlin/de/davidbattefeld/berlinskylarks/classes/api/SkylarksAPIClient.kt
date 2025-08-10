package de.davidbattefeld.berlinskylarks.classes.api

import de.davidbattefeld.berlinskylarks.global.AUTH_HEADER
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.URLBuilder
import io.ktor.http.headers

abstract class SkylarksAPIClient: AbstractAPIClient() {
    override val API_URL = "https://tib-baseball.de"
    override val authKey = AUTH_HEADER

    override fun URLBuilder.addAuthorizationParameters() {}

    override fun HttpRequestBuilder.addRequestHeaders() {
        headers {
            header(key = "X-Authorization", value = authKey)
        }
    }
}