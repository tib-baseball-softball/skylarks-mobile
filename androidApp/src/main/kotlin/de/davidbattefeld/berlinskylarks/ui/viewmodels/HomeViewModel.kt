package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.model.BSMTeam
import de.berlinskylarks.shared.database.repository.BSMTeamRepository
import de.berlinskylarks.shared.database.repository.HomeDatasetRepository
import de.berlinskylarks.shared.utility.BSMUtility
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel(assistedFactory = HomeViewModel.Factory::class)
class HomeViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val bsmTeamRepository: BSMTeamRepository,
    private val homeDatasetRepository: HomeDatasetRepository,
    private val workManagerTiBRepository: WorkManagerTiBRepository,
) : GenericViewModel(userPreferencesRepository) {
    val favoriteTeamID = userPreferencesRepository.userPreferencesFlow.map {
        it.favoriteTeamID
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = BSMUtility.NON_EXISTENT_ID
        )

    val favoriteTeam = favoriteTeamID.flatMapLatest { favoriteTeamID ->
        bsmTeamRepository.getTeamByID(favoriteTeamID).map {
            it?.toBSMTeam()
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null
        )

    val selectedSeason =
        userPreferencesRepository.userPreferencesFlow.map { prefs ->
            prefs.season
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = BSMAPIClient.DEFAULT_SEASON,
            )

    val homeDatasets = combine(
        favoriteTeamID,
        selectedSeason
    ) { favoriteTeamID, selectedSeason ->
        Pair(first = favoriteTeamID, second = selectedSeason)
    }
        .flatMapLatest {
            homeDatasetRepository.getHomeDatasetsByTeamIDAndSeason(
                teamID = it.first,
                season = it.second
            )
        }.map { homeDatasetEntities ->
            homeDatasetEntities.map {
                it.toHomeDataset()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = emptyList(),
        )

    val teamOptions: StateFlow<List<BSMTeam>> =
        selectedSeason.flatMapLatest { season ->
            bsmTeamRepository.getTeamsBySeason(season).map { entities ->
                entities.map {
                    it.toBSMTeam()
                }
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList(),
            )

    fun onTeamFilterChanged(teamID: Int) {
        viewModelScope.launch {
            userPreferencesRepository.updateFavoriteTeam(teamID)
        }
    }

    fun loadHomeData() {
        viewModelScope.launch {
            workManagerTiBRepository.syncHomeDatasets(
                teamID = favoriteTeamID.value,
                season = selectedSeason.value,
            )
        }
    }

    init {
        viewModelScope.launch {
            val existingTeam =
                bsmTeamRepository.getTeamsBySeason(selectedSeason.value).firstOrNull()
            if (existingTeam.isNullOrEmpty()) {
                workManagerTiBRepository.syncBSMTeams(selectedSeason.value)
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): HomeViewModel
    }
}