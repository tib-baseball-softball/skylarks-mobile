package de.davidbattefeld.berlinskylarks.domain.service

import androidx.annotation.DrawableRes
import de.berlinskylarks.shared.data.model.Game
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.global.baseballClubList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class GameDecorator(val game: Game) {
    // secondary properties are not supplied by the BSM API, instead computed by class methods
    var localisedDate: String? = null
    var skyLarksAreHomeTeam = false
    var skylarksWin = false
    var isDerby = false
    var isExternalGame = false
    var homeTeamWin = false

    @DrawableRes
    var homeLogo = R.drawable.app_home_team_logo
    @DrawableRes
    var roadLogo = R.drawable.app_road_team_logo

    fun setCorrectLogos() {
        // quick fix to prevent crash, not sure why default value is not set here
        homeLogo = R.drawable.app_home_team_logo
        roadLogo = R.drawable.app_road_team_logo

        for (club in baseballClubList) {
            if (game.away_team_name.contains(club.name, ignoreCase = true)) {
                roadLogo = club.logo
            }
            if (game.home_team_name.contains(club.name,ignoreCase = true)) {
                homeLogo = club.logo
            }
        }
    }

    fun parseGameTimeString(): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("y-M-dd HH:mm:ss Z")
        return LocalDateTime.parse(game.time, formatter)
    }

    fun addDate() {
        val gameDate = parseGameTimeString()
        localisedDate = gameDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
    }

    fun determineGameStatus() {
        if (game.home_team_name.contains("Skylarks") && (!game.away_team_name.contains("Skylarks")))  {
            skyLarksAreHomeTeam = true
            isDerby = false
        } else if (!game.home_team_name.contains("Skylarks") && (game.away_team_name.contains("Skylarks"))) {
            skyLarksAreHomeTeam = false
            isDerby = false
        } else if (game.home_team_name.contains("Skylarks") && (game.away_team_name.contains("Skylarks"))) {
            skyLarksAreHomeTeam = true
            isDerby = true
        } else if (!game.home_team_name.contains("Skylarks") && (!game.away_team_name.contains("Skylarks"))) {
            isDerby = false
            isExternalGame = true
        }

        homeTeamWin = (game.home_runs ?: 0) > (game.away_runs ?: 0)

        skylarksWin = if (skyLarksAreHomeTeam) {
            homeTeamWin
        } else {
            !homeTeamWin
        }
    }

    fun getGameResultIndicatorText(): String {
        if (game.human_state.contains("geplant")) {
            return "TBD"
        } else if (game.human_state.contains("ausgefallen")) {
            return "PPD"
        } else if (
            game.human_state.contains("gespielt") ||
            game.human_state.contains("Forfeit") ||
            game.human_state.contains("Nichtantreten") ||
            game.human_state.contains("Wertung") ||
            game.human_state.contains("Rückzug") ||
            game.human_state.contains("Ausschluss")
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