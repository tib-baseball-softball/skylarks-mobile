package de.berlinskylarks.shared.data.model.tib

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TibLeague(
    val name: String,
    val acronym: String,
    val season: Int,
    @SerialName("league_id") val leagueID: Int,
)
