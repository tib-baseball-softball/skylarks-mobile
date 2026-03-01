package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeagueEntry(
    val id: Int,
    val team: LeagueEntryTeam?,
    val league: League?,
) {
    /**
     * Custom class because fields differ too much in different responses.
     */
    @Serializable
    data class LeagueEntryTeam(
        val id: Int?,
        val name: String,
        @SerialName("short_name")
        val shortName: String,
        val clubs: List<CompactClub>,
        @SerialName("league_entries")
        val leagueEntries: List<LeagueEntry>? = null,
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