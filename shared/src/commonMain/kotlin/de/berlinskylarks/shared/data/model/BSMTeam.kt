package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * BSM: `Mannschaft`
 */
@Serializable
data class BSMTeam(
    val id: Int,
    val name: String,
    val season: Int,
    val pool: Boolean,
    @SerialName("human_state")
    val humanState: String,
    @SerialName("short_name")
    val shortName: String,
    @SerialName("league_entries")
    val leagueEntries: List<LeagueEntry>?,
)