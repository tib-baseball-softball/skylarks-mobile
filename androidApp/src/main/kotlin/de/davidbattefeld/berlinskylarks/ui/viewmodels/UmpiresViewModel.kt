package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.License
import de.berlinskylarks.shared.database.repository.LicenseRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = UmpiresViewModel.Factory::class)
class UmpiresViewModel @AssistedInject constructor(
    licenseRepository: LicenseRepository,
    userPreferencesRepository: UserPreferencesRepository,
) : GenericViewModel(userPreferencesRepository) {
    val licenses: StateFlow<List<License>> =
        licenseRepository.getUmpireLicenses()
            .map { list -> list.map { it.toLicense() }.sortedBy { it.level } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = emptyList(),
            )

    @AssistedFactory
    interface Factory {
        fun create(): UmpiresViewModel
    }
}