package de.davidbattefeld.berlinskylarks.ui.news

import android.icu.util.Calendar
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.TiBTeamRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import de.davidbattefeld.berlinskylarks.ui.viewmodels.GenericViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = NewsViewModel.Factory::class)
class NewsViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    gameReportRepository: GameReportRepository,
    tiBTeamRepository: TiBTeamRepository,
    private val workManagerTiBRepository: WorkManagerTiBRepository,
) : GenericViewModel(userPreferencesRepository) {
    val possibleSeasons = (2020..Calendar.getInstance().get(Calendar.YEAR)).toList()

    val filteredTeamID = MutableStateFlow<Int?>(null)

    /**
     * Season is here intentionally not connected to user preferences,
     * as reading older game reports makes sense even in a different season context.
     */
    val selectedSeason = MutableStateFlow<Int?>(null)

    val tibTeams: StateFlow<List<SkylarksTeam>> = tiBTeamRepository.getAllTeams()
        .map { entities ->
            entities.map { it.toTeam() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val gameReports: StateFlow<List<GameReport>> = combine(
        selectedSeason,
        filteredTeamID
    ) { season, teamID ->
        GameReportFilter(season, teamID)
    }.flatMapLatest { filter ->
        gameReportRepository.getGameReportsStream(
            season = if (filter.season == null) {
                filter.season
            } else {
                filter.season.toString()
            },
            team = filter.team
        )
            .map { entities ->
                entities.map { it.toGameReport() }
            }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val selectedTeam: StateFlow<SkylarksTeam?> = combine(filteredTeamID, tibTeams) { id, teams ->
        teams.find { it.id == id }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    fun refresh() {
        viewModelScope.launch {
            workManagerTiBRepository.syncTiBTeams()
            workManagerTiBRepository.syncGameReports()
        }
    }

    fun onSeasonChanged(season: Int?) {
        selectedSeason.value = season
    }

    fun onTeamFilterChanged(teamID: Int?) {
        filteredTeamID.value = teamID
    }

    data class GameReportFilter(
        val season: Int? = null,
        val team: Int? = null
    )

    @AssistedFactory
    interface Factory {
        fun create(): NewsViewModel
    }
}