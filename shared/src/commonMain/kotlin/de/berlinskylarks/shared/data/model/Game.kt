package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    override var id: Int,
    var match_id: String,
    var planned_innings: Int,
    var time: String,
    var league_id: Int,
    var home_runs: Int?,
    var away_runs: Int?,
    var home_team_name: String,
    var away_team_name: String,
    var human_state: String,
    var scoresheet_url: String?,
    var field: Field?,
    var league: League,
    var home_league_entry: LeagueEntry,
    var away_league_entry: LeagueEntry,
    var umpire_assignments: List<Umpire_Assignments>,
    var scorer_assignments: List<Scorer_Assignments>,
): JSONDataObject {
    @Serializable
    data class LeagueEntry(var team: Team)
    @Serializable
    data class Umpire_Assignments(var license: License)
    @Serializable
    data class Scorer_Assignments(var license: License)

    @Serializable
    data class License (
        var person: Person,
        var number: String,
    )

    @Serializable
    data class Person (
        var first_name: String,
        var last_name: String,
    )
    //beware the other version of this class (own file)
    @Serializable
    data class Team(
        var name: String,
    )
}