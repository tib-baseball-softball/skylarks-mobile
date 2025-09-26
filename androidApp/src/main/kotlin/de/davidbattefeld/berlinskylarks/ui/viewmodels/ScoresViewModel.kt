package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.data.model.MatchBoxScore
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.data.service.GameReportService
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.GameRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.domain.model.UserCalendar
import de.davidbattefeld.berlinskylarks.domain.service.CalendarService
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.global.BOGUS_ID
import de.davidbattefeld.berlinskylarks.testdata.testLeagueGroup
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ScoresViewModel.Factory::class)
class ScoresViewModel @AssistedInject constructor(
    private val gameRepository: GameRepository,
    private val gameReportRepository: GameReportRepository,
    private val matchAPIClient: MatchAPIClient,
    private val leagueGroupsAPIClient: LeagueGroupsAPIClient,
    private val gameReportService: GameReportService,
    userPreferencesRepository: UserPreferencesRepository
) : GenericViewModel(userPreferencesRepository) {
    var games = mutableStateListOf<GameDecorator>()
    var skylarksGames = mutableStateListOf<GameDecorator>()
    var leagueGroups = mutableStateListOf<LeagueGroup>()
    var filteredLeagueGroup by mutableStateOf<LeagueGroup>(testLeagueGroup)
    var userCalendars = mutableStateListOf<UserCalendar>()

    var currentBoxScore by mutableStateOf<MatchBoxScore?>(null)

    // TODO: read route argument
    var currentGameReport: StateFlow<GameReport?> =
        gameReportRepository.getGameReportByGameID("")
            .map { it?.toGameReport() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = null
            )

    var tabState by mutableStateOf(TabState.CURRENT)

    val calendarService = CalendarService()

    // TODO: TEMP TEMP TEMP
    init {
        viewModelScope.launch {
            val empty = gameReportRepository.getAllGameReportsStream().firstOrNull().isNullOrEmpty()
            if (empty) {
                gameReportService.syncGameReports()
            }
        }
    }

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
            TabState.PREVIOUS -> "previous"
            TabState.CURRENT -> "current"
            TabState.NEXT -> "next"
            TabState.ANY -> "any"
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
            it.game.awayTeamName.contains("Skylarks", ignoreCase = true) ||
                    it.game.homeTeamName.contains("Skylarks", ignoreCase = true)
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

    fun loadBoxScoreForGame(gameID: Int) {
        viewModelScope.launch {
            currentBoxScore = gameRepository.loadGameBoxScore(gameID)
        }
    }

    enum class TabState {
        PREVIOUS,
        CURRENT,
        NEXT,
        ANY
    }

    @AssistedFactory
    interface Factory {
        fun create(): ScoresViewModel
    }
}