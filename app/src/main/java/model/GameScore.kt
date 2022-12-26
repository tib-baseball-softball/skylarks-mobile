package model

data class GameScore(
    var id: Int,
    var match_id: String,
    var time: String,
    var league_id: Int,
    var home_runs: Int?,
    var away_runs: Int?,
    var home_team_name: String,
    var away_team_name: String,
    var human_state: String,
    var scoresheet_url: String?,
    //
    //
    var home_league_entry: LeagueEntry,
    var away_league_entry: LeagueEntry,
) {
    data class LeagueEntry(
        var team: Team
    )
    //beware the other version of this class (own file)
    data class Team(
        var name: String,
    )

    //test if parameters can be modified
    fun changeRuns() {
        home_runs = 4
    }
}