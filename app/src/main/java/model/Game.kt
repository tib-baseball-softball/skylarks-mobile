package model

import androidx.annotation.DrawableRes
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.BSVBB_CLUBS
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Game(
    var id: Int,
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
) {
    // secondary properties are not supplied by the BSM API, instead computed by class methods
    var localisedDate: String? = null
    var skyLarksAreHomeTeam = false
    var skylarksWin = false
    var isDerby = false
    var isExternalGame = false
    var homeTeamWin = false

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

    @DrawableRes
    var homeLogo = R.drawable.app_home_team_logo
    @DrawableRes
    var roadLogo = R.drawable.app_road_team_logo

    fun setCorrectLogos() {
        // quick fix to prevent crash, not sure why default value is not set here
        homeLogo = R.drawable.app_home_team_logo
        roadLogo = R.drawable.app_road_team_logo

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
        val gameDate = LocalDateTime.parse(time, formatter)

        localisedDate = gameDate.format(DateTimeFormatter.ofLocalizedDateTime(
            java.time.format.FormatStyle.MEDIUM))
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
        if (human_state.contains("geplant")) {
            return "TBD"
        } else if (human_state.contains("ausgefallen")) {
            return "PPD"
        } else if (
            human_state.contains("gespielt") ||
            human_state.contains("Forfeit") ||
            human_state.contains("Nichtantreten") ||
            human_state.contains("Wertung") ||
            human_state.contains("Rückzug") ||
            human_state.contains("Ausschluss")
        ) {
            if (isExternalGame) {
                return "F"
            } else {
                if (isDerby) {
                    return "heart"
                }
                return if (skylarksWin) {
                    "W"
                } else {
                    "L"
                }

            }
        }
        return "X"
    }
}