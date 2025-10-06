package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Linescore(
    @SerialName("match_id")
    val matchId: String,
    @SerialName("played_innings")
    val playedInnings: Int,
    val away: LinescoreEntry,
    val home: LinescoreEntry
)

@Serializable
data class LinescoreEntry(
    @SerialName("league_entry")
    val leagueEntry: LeagueEntry,
    val innings: List<String>,
    val runs: Int,
    val hits: Int,
    val errors: Int
)