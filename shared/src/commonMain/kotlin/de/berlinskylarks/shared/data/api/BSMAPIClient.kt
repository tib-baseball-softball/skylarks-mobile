package de.berlinskylarks.shared.data.api

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

abstract class BSMAPIClient(authKey: String) : AbstractAPIClient(authKey) {
    override val API_URL = "https://bsm.baseball-softball.de"

    companion object {
        const val CLUB_ID = 485
        // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
        const val BSVBB_ORGANIZATION_ID = 9
        const val DBV_ORGANIZATION_ID = 1

        const val SEASON_FILTER = "filters[seasons][]"
        const val GAMEDAY_FILTER = "filters[gamedays][]"
        const val LEAGUE_FILTER = "filters[leagues][]"
        const val ORGANIZATION_FILTER = "filters[organizations][]"
        const val TEAM_SEARCH = "search"

        @OptIn(ExperimentalTime::class)
        val DEFAULT_SEASON = Clock.System.now().toLocalDateTime(timeZone = TimeZone.of("Europe/Berlin")).year
    }

    override fun URLBuilder.addAuthorizationParameters() {
        parameters.append("api_key", authKey)
    }

    override fun HttpRequestBuilder.addRequestHeaders() {}
}