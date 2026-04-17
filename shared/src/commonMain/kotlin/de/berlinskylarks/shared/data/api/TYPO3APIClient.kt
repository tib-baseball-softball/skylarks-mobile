package de.berlinskylarks.shared.data.api

import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.shared.database.repository.ConfigurationRepository
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.firstOrNull

abstract class TYPO3APIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String
) : AbstractAPIClient(configurationRepository, authKey) {
    override val API_URL = "https://www.tib-baseball.de"

    override fun URLBuilder.addAuthorizationParameters() {}

    override fun HttpRequestBuilder.addRequestHeaders() {}

    override suspend fun getAPIURLFromRemoteConfiguration(): String? {
        val config = configurationRepository.getConfigurationsByApplicationContext(
            ApplicationContext.production
        ).firstOrNull()?.map { it.toConfigurationDTO() }?.firstOrNull() ?: return null

        return config.apiURLS.cmsURL
    }
}