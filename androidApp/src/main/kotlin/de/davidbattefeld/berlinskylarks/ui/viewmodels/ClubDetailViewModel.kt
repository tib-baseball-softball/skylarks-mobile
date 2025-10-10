package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.Club
import de.berlinskylarks.shared.database.repository.ClubRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.ClubDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = ClubDetailViewModel.Factory::class)
class ClubDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: ClubDetail,
    userPreferencesRepository: UserPreferencesRepository,
    clubRepository: ClubRepository,
) : GenericViewModel(userPreferencesRepository) {
    val club: StateFlow<Club?> = clubRepository.getClubByID(navKey.id)
        .map { it?.toClub() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: ClubDetail): ClubDetailViewModel
    }
}