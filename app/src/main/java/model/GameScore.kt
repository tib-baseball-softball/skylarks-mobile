package model

import androidx.annotation.DrawableRes
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.BSVBB_CLUBS
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    // secondary properties are not supplied by the BSM API, instead computed by class methods
    var gameDate: LocalDateTime? = null
    var skyLarksAreHomeTeam = false
    var skylarksWin = false
    var isDerby = false
    var isExternalGame = false
    var homeTeamWin = false

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

    fun addDate() {
        val formatter = DateTimeFormatter.ofPattern("y-M-dd HH:mm:ss Z")
        gameDate = LocalDateTime.parse(time, formatter)
    }

    fun determineGameStatus() {
        if (home_team_name.contains("Skylarks") && (!away_team_name.contains("Skylarks")))  {
            skyLarksAreHomeTeam = true
            isDerby = false
        } else if (!home_team_name.contains("Skylarks") && (away_team_name.contains("Skylarks"))) {
            skyLarksAreHomeTeam = false
            isDerby = false
        } else if (home_team_name.contains("Skylarks") && (away_team_name.contains("Skylarks"))) {
            skyLarksAreHomeTeam = true
            isDerby = true
        } else if (!home_team_name.contains("Skylarks") && (!away_team_name.contains("Skylarks"))) {
            isDerby = false
            isExternalGame = true
        }

        homeTeamWin = (home_runs ?: 0) > (away_runs ?: 0)

        skylarksWin = if (skyLarksAreHomeTeam) {
            homeTeamWin
        } else {
            !homeTeamWin
        }
    }

    fun getGameResultIndicatorText(): String {
        var str = "X"

        if (human_state.contains("geplant")) {
            str = "TBD"
        } else if (human_state.contains("ausgefallen")) {
            str = "PPD"
        } else if (
            human_state.contains("gespielt") ||
            human_state.contains("Forfeit") ||
            human_state.contains("Nichtantreten") ||
            human_state.contains("Wertung") ||
            human_state.contains("Rückzug") ||
            human_state.contains("Ausschluss")
        ) {
            if (isExternalGame) {
                str = "F"
            } else {
                str = if (skylarksWin) {
                    "W"
                } else {
                    "L"
                }
                if (isDerby) {
                    str = "heart"
                }
            }
        }
        return str
    }
}