package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueEntry(
    var team: LeagueEntryTeam?,
    var league: League?,
) {
    /**
     * Custom class because fields differ too much in different responses.
     */
    @Serializable
    data class LeagueEntryTeam(
        var id: Int?,
        var name: String,
        var short_name: String,
        var league_entries: List<LeagueEntry>? = null,
    )
}