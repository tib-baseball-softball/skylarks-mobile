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
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.database.repository.GameRepository
import de.berlinskylarks.shared.database.repository.LeagueGroupRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import de.davidbattefeld.berlinskylarks.domain.model.UserCalendar
import de.davidbattefeld.berlinskylarks.domain.service.CalendarService
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.testdata.LEAGUE_GROUP_ALL
import de.davidbattefeld.berlinskylarks.ui.utility.ViewState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ScoresViewModel.Factory::class)
class ScoresViewModel @AssistedInject constructor(
    gameRepository: GameRepository,
    userPreferencesRepository: UserPreferencesRepository,
    workManagerTiBRepository: WorkManagerTiBRepository,
    leagueGroupRepository: LeagueGroupRepository,
    private val calendarService: CalendarService,
) : GenericViewModel(userPreferencesRepository) {

    var games: StateFlow<List<GameDecorator>> =
        gameRepository.getGamesByFilter()
            .map { dbEntities ->
                dbEntities.map {
                    GameDecorator(game = it.json).decorate()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList(),
            )

    var leagueGroups: StateFlow<List<LeagueGroup>> =
        leagueGroupRepository.getAllLeagueGroups()
            .map { dbEntities ->
                dbEntities.map {
                    it.toLeagueGroup()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList(),
            )

    var skylarksGames = mutableStateListOf<GameDecorator>()
    var filteredLeagueGroup by mutableStateOf<LeagueGroup>(LEAGUE_GROUP_ALL)

    var tabState by mutableStateOf(TabState.CURRENT)

    var userCalendars = mutableStateListOf<UserCalendar>()
    val calendarService = CalendarService()

//    init {
//        viewModelScope.launch {
//            val season = userPreferencesFlow.firstOrNull()?.season ?: BSMAPIClient.DEFAULT_SEASON
//            // one-time request to ensure up-to-date game data
//            workManagerTiBRepository.syncScores(season = season)
//
//            val existingLeagueGroup = leagueGroupRepository.getFirstItem().firstOrNull()
//            if (existingLeagueGroup == null) {
//                workManagerTiBRepository.syncLeagueGroups(season)
//            }
//        }
//    }

    fun load() {
        viewModelScope.launch {
            viewState = if (games.value.isNotEmpty()) ViewState.Found else ViewState.NoResults
        }
    }

    fun onLeagueFilterChanged(leagueGroup: LeagueGroup) {
        filteredLeagueGroup = leagueGroup
    }

    fun loadCalendars() {
        viewModelScope.launch {
            userCalendars.clear()
            userCalendars.addAll(calendarService.loadUserCalendars())
        }
    }

    fun addGamesToCalendar(id: Long) {
        val gamesToUse = games

        viewModelScope.launch {
            calendarService.addGamesToCalendar(gameDecorators = gamesToUse.value, calendarID = id)
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