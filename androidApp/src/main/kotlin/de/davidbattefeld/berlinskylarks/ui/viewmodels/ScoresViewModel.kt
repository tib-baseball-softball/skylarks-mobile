package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.domain.model.UserCalendar
import de.davidbattefeld.berlinskylarks.domain.service.CalendarService
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ScoresViewModel(userPreferencesRepository: UserPreferencesRepository) :
    GenericViewModel(userPreferencesRepository) {
    var games = mutableStateListOf<GameDecorator>()
    var skylarksGames = mutableStateListOf<GameDecorator>()
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var filteredLeagueGroup by mutableStateOf<LeagueGroup>(testLeagueGroup)
    var userCalendars = mutableStateListOf<UserCalendar>()

    var tabState by mutableIntStateOf(1)

    private val matchAPIClient = MatchAPIClient(authKey = BSM_API_KEY)
    private val leagueGroupsAPIClient = LeagueGroupsAPIClient(authKey = BSM_API_KEY)
    val calendarService = CalendarService()

    override fun load() {
        leagueGroups.clear()

        viewModelScope.launch {
            val season = userPreferencesFlow.firstOrNull()?.season ?: BSMAPIClient.DEFAULT_SEASON
            viewState = ViewState.Loading

            leagueGroups.addAll(leagueGroupsAPIClient.loadLeagueGroupsForClub(season))
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
        val season = userPreferencesFlow.firstOrNull()?.season ?: BSMAPIClient.DEFAULT_SEASON

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
            games.addAll(
                matchAPIClient.loadGamesForClub(season, gamedays).map { GameDecorator(it) }
            )
        } else {
            games.addAll(
                matchAPIClient.loadAllGames(
                    season = season,
                    gamedays = gamedays,
                    leagues = filteredLeagueGroup.id
                ).map { GameDecorator(it) }
            )
        }
        games.forEach {
            it.addDate()
            it.determineGameStatus()
            it.setCorrectLogos()
        }
        skylarksGames.addAll(games.filter {
            it.game.away_team_name.contains("Skylarks", ignoreCase = true) ||
                    it.game.home_team_name.contains("Skylarks", ignoreCase = true)
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
            calendarService.addGamesToCalendar(gameDecorators = gamesToUse, calendarID = id)
        }
    }
}