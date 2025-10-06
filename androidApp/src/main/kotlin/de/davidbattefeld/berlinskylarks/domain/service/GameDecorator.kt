package de.davidbattefeld.berlinskylarks.domain.service

import androidx.annotation.DrawableRes
import de.berlinskylarks.shared.data.model.Game
import de.berlinskylarks.shared.utility.DateTimeUtility
import de.davidbattefeld.berlinskylarks.R
import de.davidbattefeld.berlinskylarks.data.utility.AndroidDateTimeUtility
import de.davidbattefeld.berlinskylarks.global.baseballClubList
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
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

    fun decorate(): GameDecorator {
        addDate()
        determineGameStatus()
        setCorrectLogos()
        return this
    }

    private fun setCorrectLogos(): GameDecorator {
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

    /**
     * Formats the game time into a localized, human-readable date and time string.
     *
     * kotlinx.datetime does not have built-in support for locale-sensitive formatting.
     * The recommended approach on Android is to convert the Instant back to the java.time API
     * and use the platform's robust formatting capabilities.
     */
    private fun addDate(): GameDecorator {
        val gameInstant = DateTimeUtility.parseBSMDateTimeString(game.time)
        localisedDate = AndroidDateTimeUtility.formatDate(gameInstant)
        return this
    }

    private fun determineGameStatus(): GameDecorator {
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