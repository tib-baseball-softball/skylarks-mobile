package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.database.repository.ConfigurationRepository
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder

/**
 * Client for internal BSM API relay (for custom endpoints).
 */
abstract class BSMRelayAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String = ""
) : AbstractAPIClient(configurationRepository, authKey) {
    override val API_URL = "https://bsm.berlinskylarks.de"

    override fun URLBuilder.addAuthorizationParameters() {}

    override fun HttpRequestBuilder.addRequestHeaders() {}

    override suspend fun getAPIURLFromRemoteConfiguration(): String? {
//        val config = configurationRepository.getConfigurationsByApplicationContext(
//            ApplicationContext.production
//        ).firstOrNull()?.map { it.toConfigurationDTO() }?.firstOrNull() ?: return null
//
//        return config.apiURLS.dpURL

        // re-add when provided by service
        return API_URL
    }
}