package de.davidbattefeld.berlinskylarks.ui.viewmodels

import android.icu.util.Calendar
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository

@HiltViewModel(assistedFactory = SettingsViewModel.Factory::class)
class SettingsViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    private val workManagerTiBRepository: WorkManagerTiBRepository,
) : GenericViewModel(userPreferencesRepository) {
    // 2021 is the first year with the new team name
    val possibleSeasons = (2021..Calendar.getInstance().get(Calendar.YEAR)).toList()

    /**
     * Requests a full update of all data for the given season.
     * Home datasets are excluded as they depend on team ID.
     */
    fun onDataFullUpdateRequest(season: Int) {
        workManagerTiBRepository.syncBSMTeams(season)
        workManagerTiBRepository.syncPlayers()
        workManagerTiBRepository.syncClubData()
        workManagerTiBRepository.syncScores(season)
        workManagerTiBRepository.syncGameReports()
        workManagerTiBRepository.syncLeagueGroups(season)
        workManagerTiBRepository.syncTiBTeams()
    }

    @AssistedFactory
    interface Factory {
        fun create(): SettingsViewModel
    }
}