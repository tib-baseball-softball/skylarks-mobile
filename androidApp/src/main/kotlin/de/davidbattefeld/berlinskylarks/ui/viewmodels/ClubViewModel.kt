package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.model.Club
import de.berlinskylarks.shared.database.repository.ClubRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import de.davidbattefeld.berlinskylarks.ui.nav.Club as NavClub

@HiltViewModel(assistedFactory = ClubViewModel.Factory::class)
class ClubViewModel @AssistedInject constructor(
    @Assisted val navKey: NavClub,
    clubRepository: ClubRepository,
    userPreferencesRepository: UserPreferencesRepository,
    workManagerTiBRepository: WorkManagerTiBRepository,
) : GenericViewModel(userPreferencesRepository) {
    val club: StateFlow<Club?> = clubRepository.getClubByID(BSMAPIClient.SKYLARKS_CLUB_ID)
        .map { it?.toClub() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            val existingClubData =
                clubRepository.getClubByID(BSMAPIClient.SKYLARKS_CLUB_ID).firstOrNull()

            if (existingClubData == null) {
                workManagerTiBRepository.syncClubData()
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: NavClub): ClubViewModel
    }
}