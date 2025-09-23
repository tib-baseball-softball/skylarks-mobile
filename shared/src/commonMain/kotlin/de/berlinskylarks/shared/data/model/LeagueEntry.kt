package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueEntry(
    var team: BSMTeam,
    var league: League?,
)