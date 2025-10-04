package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.enums.Gameday
import de.berlinskylarks.shared.data.model.LeagueGroup
import de.berlinskylarks.shared.database.repository.GameRepository
import de.berlinskylarks.shared.database.repository.LeagueGroupRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import de.davidbattefeld.berlinskylarks.domain.model.UserCalendar
import de.davidbattefeld.berlinskylarks.domain.service.CalendarService
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.testdata.LEAGUE_GROUP_ALL
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = ScoresViewModel.Factory::class)
class ScoresViewModel @AssistedInject constructor(
    gameRepository: GameRepository,
    userPreferencesRepository: UserPreferencesRepository,
    private val workManagerTiBRepository: WorkManagerTiBRepository,
    private val leagueGroupRepository: LeagueGroupRepository,
    private val calendarService: CalendarService,
) : GenericViewModel(userPreferencesRepository) {
    // -----------------Calendar Handling------------------//
    var userCalendars = mutableStateListOf<UserCalendar>()

    // -----------------Game Filters------------------//
    val selectedGameday = MutableStateFlow(Gameday.CURRENT)
    val filteredLeagueGroup = MutableStateFlow(LEAGUE_GROUP_ALL)
    val showExternalGames = MutableStateFlow(false)
    val selectedSeason =
        userPreferencesRepository.userPreferencesFlow.map { prefs ->
            prefs.season
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = BSMAPIClient.DEFAULT_SEASON,
            )

    var games: StateFlow<List<GameDecorator>> =
        combine(
            selectedGameday,
            filteredLeagueGroup,
            showExternalGames,
            selectedSeason,
        ) { selectedGameday, filteredLeagueGroup, showExternalGames, selectedSeason ->
            GameFilter(
                selectedGameday, filteredLeagueGroup, showExternalGames, selectedSeason
            )
        }
            .flatMapLatest { (selectedGameday, filteredLeagueGroup, showExternalGames) ->
                gameRepository.getGamesByFilter(
                    gameday = selectedGameday,
                    leagueGroupID = filteredLeagueGroup.id,
                    external = showExternalGames,
                    season = selectedSeason.value,
                )
                    .map { dbEntities ->
                        dbEntities.map {
                            GameDecorator(game = it.json).decorate()
                        }
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

    fun refresh() {
        viewModelScope.launch {
            // one-time request to ensure up-to-date game data
            workManagerTiBRepository.syncScores(season = selectedSeason.value)

            val existingLeagueGroup = leagueGroupRepository.getFirstItem().firstOrNull()
            if (existingLeagueGroup == null) {
                workManagerTiBRepository.syncLeagueGroups(selectedSeason.value)
            }
        }
    }

    fun onLeagueFilterChanged(leagueGroup: LeagueGroup) {
        filteredLeagueGroup.value = leagueGroup
    }

    fun onGamedayChanged(gameday: Gameday) {
        selectedGameday.value = gameday
    }

    fun onShowExternalGamesChanged(showExternalGames: Boolean) {
        this.showExternalGames.value = showExternalGames
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

    data class GameFilter(
        val selectedGameday: Gameday,
        val filteredLeagueGroup: LeagueGroup,
        val showExternalGames: Boolean,
        val selectedSeason: Int,
    )

    @AssistedFactory
    interface Factory {
        fun create(): ScoresViewModel
    }
}