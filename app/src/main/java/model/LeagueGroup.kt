package model

data class LeagueGroup(
    var id: Int,
    var season: Int,
    var name: String,
    var acronym: String,
    var league: League,
)