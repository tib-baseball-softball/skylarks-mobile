package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
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
        var clubs: List<CompactClub>,
        var league_entries: List<LeagueEntry>? = null,
    )

    /**
     * Club response when query parameter `compact` on matches endpoint is `true`.
     * Additional fields are not included.
     *
     * @see Club
     */
    @Serializable
    data class CompactClub(
        val id: Long,
        val name: String,
        val acronym: String,
        @SerialName("short_name")
        val shortName: String,
        @SerialName("logo_url")
        val logoURL: String?
    )
}