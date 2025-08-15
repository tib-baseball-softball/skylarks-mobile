package de.davidbattefeld.berlinskylarks.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BSMTeam(
    var id: Int,
    var name: String,
    var short_name: String,
    var league_entries: List<LeagueEntry>,
    //var current_player_list: PlayerList?
) {
    @Serializable
    data class LeagueEntry(
        var league: League,
    )
}