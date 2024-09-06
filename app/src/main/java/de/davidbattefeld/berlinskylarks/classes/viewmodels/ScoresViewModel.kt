package de.davidbattefeld.berlinskylarks.classes.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.davidbattefeld.berlinskylarks.classes.UserCalendar
import de.davidbattefeld.berlinskylarks.classes.api.BSMAPIRequest
import de.davidbattefeld.berlinskylarks.classes.api.LeagueGroupsAPIRequest
import de.davidbattefeld.berlinskylarks.classes.api.MatchAPIRequest
import de.davidbattefeld.berlinskylarks.classes.service.CalendarService
import de.davidbattefeld.berlinskylarks.enums.ViewState
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import model.Game
import model.LeagueGroup

class ScoresViewModel(application: Application) : GenericViewModel(application) {
    var games = mutableStateListOf<Game>()
    var skylarksGames = mutableStateListOf<Game>()
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var filteredLeagueGroup by mutableStateOf(testLeagueGroup)
    var userCalendars = mutableStateListOf<UserCalendar>()

    var tabState by mutableIntStateOf(1)

    private val matchAPIRequest = MatchAPIRequest()
    private val leagueGroupsAPIRequest = LeagueGroupsAPIRequest()
    val calendarService = CalendarService()

    override fun load() {
        leagueGroups.clear()

        viewModelScope.launch {
            val season = userPreferencesFlow.firstOrNull()?.season ?: BSMAPIRequest.DEFAULT_SEASON
            viewState = ViewState.Loading

            leagueGroups.addAll(leagueGroupsAPIRequest.loadLeagueGroupsForClub(season))
            loadGames()

            viewState = if (games.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }

    fun onLeagueFilterChanged(leagueGroup: LeagueGroup) {
        filteredLeagueGroup = leagueGroup
        viewModelScope.launch {
            loadGames()
        }
    }

    private suspend fun loadGames() {
        val season = userPreferencesFlow.firstOrNull()?.season ?: BSMAPIRequest.DEFAULT_SEASON

        games.clear()
        skylarksGames.clear()

        val gamedays = when (tabState) {
            0 -> "previous"
            1 -> "current"
            2 -> "next"
            3 -> "any"
            else -> {
                throw Exception("tabState has invalid value that cannot be used to determine Gameday")
            }
        }

        if (filteredLeagueGroup.id == BOGUS_ID) {
            games.addAll(matchAPIRequest.loadGamesForClub(season, gamedays))
        } else {
            games.addAll(
                matchAPIRequest.loadAllGames(
                    season = season,
                    gamedays = gamedays,
                    leagues = filteredLeagueGroup.id
                )
            )
        }
        games.forEach {
            it.addDate()
            it.determineGameStatus()
            it.setCorrectLogos()
        }
        skylarksGames.addAll(games.filter {
            it.away_team_name.contains("Skylarks", ignoreCase = true) ||
                    it.home_team_name.contains("Skylarks", ignoreCase = true)
        })
    }

    fun loadCalendars(context: Context) {
        calendarService.context = context
        viewModelScope.launch {
            userCalendars.clear()
            userCalendars.addAll(calendarService.loadUserCalendars())
        }
    }

    fun addGamesToCalendar(context: Context, id: Long) {
        val gamesToUse = games

        calendarService.context = context
        viewModelScope.launch {
            calendarService.addGamesToCalendar(games = gamesToUse, calendarID = id)
        }
    }
}