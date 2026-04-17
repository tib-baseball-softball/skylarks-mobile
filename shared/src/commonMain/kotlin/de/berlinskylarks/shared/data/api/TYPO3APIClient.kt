package de.berlinskylarks.shared.data.api

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder

abstract class TYPO3APIClient(authKey: String) : AbstractAPIClient(authKey) {
    override val API_URL = "https://tib-baseball.de"

    override fun URLBuilder.addAuthorizationParameters() {}

    override fun HttpRequestBuilder.addRequestHeaders() {}
}