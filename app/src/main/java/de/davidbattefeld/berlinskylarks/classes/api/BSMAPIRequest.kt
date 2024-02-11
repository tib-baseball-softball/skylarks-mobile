package de.davidbattefeld.berlinskylarks.classes.api

import android.icu.util.Calendar
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.URLBuilder

abstract class BSMAPIRequest: AbstractAPIRequest() {
    override val API_URL = "https://bsm.baseball-softball.de"
    override val authKey = BSM_API_KEY
    companion object {
        const val CLUB_ID = 485
        // in BSM jargon an "organisation" is a Landesverband (BSVBB in this case)
        const val BSVBB_ORGANIZATION_ID = 9
        const val DBV_ORGANIZATION_ID = 1

        const val SEASON_FILTER = "filters[seasons][]"
        const val GAMEDAY_FILTER = "filters[gamedays][]"
        const val ORGANIZATION_FILTER = "filters[organizations][]"
        const val TEAM_SEARCH = "search"

        val DEFAULT_SEASON = Calendar.getInstance().get(Calendar.YEAR)
    }

    override fun URLBuilder.addAuthorizationParameters() {
        parameters.append("api_key", authKey)
    }

    override fun HttpRequestBuilder.addRequestHeaders() {}
}