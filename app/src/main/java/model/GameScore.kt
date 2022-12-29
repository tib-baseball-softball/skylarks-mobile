package model

import androidx.annotation.DrawableRes
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.BSVBB_CLUBS

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
    var field: Field?,
    var league: League,
    var home_league_entry: LeagueEntry,
    var away_league_entry: LeagueEntry,
    var umpire_assignments: List<Umpire_Assignments>,
    var scorer_assignments: List<Scorer_Assignments>,
) {
    data class LeagueEntry(var team: Team)

    data class Umpire_Assignments(var license: License)

    data class Scorer_Assignments(var license: License)


    data class License (
        var person: Person,
        var number: String,
        )

    data class Person (
        var first_name: String,
        var last_name: String,
        )
    //beware the other version of this class (own file)
    data class Team(
        var name: String,
    )

    @DrawableRes
    var homeLogo = R.drawable.app_home_team_logo
    @DrawableRes
    var roadLogo = R.drawable.app_road_team_logo

    fun setCorrectLogos() {
        for (club in BSVBB_CLUBS) {
            if (away_team_name.contains(club.name, ignoreCase = true)) {
                roadLogo = club.logo
            }
            if (home_team_name.contains(club.name,ignoreCase = true)) {
                homeLogo = club.logo
            }
        }
    }
}