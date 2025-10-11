package de.berlinskylarks.shared.data.api

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder

/**
 * Just for basic public API GET endpoints, any sophisticated interactions with the Pocketbase API
 * will be handled by a dedicated setup.
 *
 * MARK: no authentication
 */
abstract class DiamondPlannerAPIClient(authKey: String = "") : AbstractAPIClient(authKey) {
    override val API_URL = "https://pb.berlinskylarks.de"

    override fun URLBuilder.addAuthorizationParameters() {}

    override fun HttpRequestBuilder.addRequestHeaders() {}
}