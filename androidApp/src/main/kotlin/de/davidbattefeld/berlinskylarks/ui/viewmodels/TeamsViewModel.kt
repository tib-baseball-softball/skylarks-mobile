package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.SkylarksTeam
import de.berlinskylarks.shared.database.repository.TiBTeamRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = TeamsViewModel.Factory::class)
class TeamsViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    tiBTeamRepository: TiBTeamRepository,
    workManagerTiBRepository: WorkManagerTiBRepository,
) : GenericViewModel(userPreferencesRepository) {
    var teams: StateFlow<List<SkylarksTeam>> = tiBTeamRepository.getAllTeams()
        .map { dbTeam ->
            dbTeam.map {
                SkylarksTeam(
                    id = it.id,
                    name = it.name,
                    leagueID = it.leagueID,
                    bsmShortName = it.bsmShortName,
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = listOf()
        )

    init {
        val firstTeam = teams.value.firstOrNull()
        if (firstTeam == null) {
            workManagerTiBRepository.syncTiBTeams()
            workManagerTiBRepository.syncPlayers()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): TeamsViewModel
    }
}