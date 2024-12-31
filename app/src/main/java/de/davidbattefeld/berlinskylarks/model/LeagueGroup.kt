package de.davidbattefeld.berlinskylarks.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueGroup(
    var id: Int,
    var season: Int,
    var name: String,
    var acronym: String,
    var league: League,
)