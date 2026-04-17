package de.berlinskylarks.shared.data.api

import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.shared.database.repository.ConfigurationRepository
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

abstract class BSMAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String
) : AbstractAPIClient(configurationRepository, authKey) {
    override val API_URL = "https://bsm.baseball-softball.de"

    companion object {
        const val SKYLARKS_CLUB_ID = 485

        // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
        const val BSVBB_ORGANIZATION_ID = 9
        const val DBV_ORGANIZATION_ID = 1

        const val SEASON_FILTER = "filters[seasons][]"
        const val GAMEDAY_FILTER = "filters[gamedays][]"
        const val LEAGUE_FILTER = "filters[leagues][]"
        const val ORGANIZATION_FILTER = "filters[organizations][]"
        const val COMPACT_FILTER = "compact"
        const val TEAM_SEARCH = "search"
        const val DBV_TIME_ZONE = "Europe/Berlin"

        @OptIn(ExperimentalTime::class)
        val DEFAULT_SEASON =
            Clock.System.now().toLocalDateTime(timeZone = TimeZone.of(DBV_TIME_ZONE)).year
    }

    override fun URLBuilder.addAuthorizationParameters() {
        parameters.append("api_key", authKey)
    }

    override fun HttpRequestBuilder.addRequestHeaders() {}

    override suspend fun getAPIURLFromRemoteConfiguration(): String? {
        val config = configurationRepository.getConfigurationsByApplicationContext(
            ApplicationContext.production
        ).firstOrNull()?.map { it.toConfigurationDTO() }?.firstOrNull() ?: return null

        return config.apiURLS.bsmURL
    }
}