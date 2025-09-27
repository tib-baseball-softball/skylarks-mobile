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

    fun setCorrectLogos(): GameDecorator {
        // quick fix to prevent crash, not sure why default value is not set here
        homeLogo = R.drawable.app_home_team_logo
        roadLogo = R.drawable.app_road_team_logo

        for (club in baseballClubList) {
            if (game.awayTeamName.contains(club.name, ignoreCase = true)) {
                roadLogo = club.logo
            }
            if (game.homeTeamName.contains(club.name, ignoreCase = true)) {
                homeLogo = club.logo
            }
        }
        return this
    }

    fun parseGameTimeString(): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("y-M-dd HH:mm:ss Z")
        return LocalDateTime.parse(game.time, formatter)
    }

    fun addDate(): GameDecorator {
        val gameDate = parseGameTimeString()
        localisedDate = gameDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
        return this
    }

    fun determineGameStatus(): GameDecorator {
        if (game.homeTeamName.contains("Skylarks") && (!game.awayTeamName.contains("Skylarks"))) {
            skyLarksAreHomeTeam = true
            isDerby = false
        } else if (!game.homeTeamName.contains("Skylarks") && (game.awayTeamName.contains("Skylarks"))) {
            skyLarksAreHomeTeam = false
            isDerby = false
        } else if (game.homeTeamName.contains("Skylarks") && (game.awayTeamName.contains("Skylarks"))) {
            skyLarksAreHomeTeam = true
            isDerby = true
        } else if (!game.homeTeamName.contains("Skylarks") && (!game.awayTeamName.contains("Skylarks"))) {
            isDerby = false
            isExternalGame = true
        }

        homeTeamWin = (game.homeRuns ?: 0) > (game.awayRuns ?: 0)

        skylarksWin = if (skyLarksAreHomeTeam) {
            homeTeamWin
        } else {
            !homeTeamWin
        }
        return this
    }

    fun getGameResultIndicatorText(): String {
        if (game.humanState.contains("geplant")) {
            return "TBD"
        } else if (game.humanState.contains("ausgefallen")) {
            return "PPD"
        } else if (
            game.humanState.contains("gespielt") ||
            game.humanState.contains("Forfeit") ||
            game.humanState.contains("Nichtantreten") ||
            game.humanState.contains("Wertung") ||
            game.humanState.contains("Rückzug") ||
            game.humanState.contains("Ausschluss")
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