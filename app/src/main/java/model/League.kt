package model

data class LeagueGroup(
    var id: Int,
    var season: Int,
    var name: String,
    var acronym: String,
    var league: League,
)

data class League(
    var id: Int,
    var season: Int,
    var name: String,
    var acronym: String,
    var sport: String,
    var classification: String,
    var age_group: String?
)
