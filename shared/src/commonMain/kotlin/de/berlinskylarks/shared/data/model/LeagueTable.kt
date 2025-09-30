package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LeagueTable(
    var league_id: Int,
    var league_name: String,
    var season: Int,
    var rows: List<Row>,
) {
    @Serializable
    data class Row(
        var rank: String,
        var team_name: String,
        var short_team_name: String,
        var match_count: Int, //those might be optionals!
        var wins_count: Double, //apparently sometimes there are half wins
        var losses_count: Double,
        var quota: String,
        var games_behind: String,
        var streak: String,
    )
}