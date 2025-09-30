package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * BSM: `Mannschaft`
 */
@Serializable
data class BSMTeam(
    var id: Int,
    var name: String,
    @SerialName("short_name")
    var shortName: String,
    @SerialName("league_entries")
    var leagueEntries: List<LeagueEntry>?,
)