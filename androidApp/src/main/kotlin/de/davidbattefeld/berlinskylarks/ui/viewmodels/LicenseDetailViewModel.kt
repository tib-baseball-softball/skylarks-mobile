package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.License
import de.berlinskylarks.shared.database.repository.LicenseRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.ui.nav.LicenseDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = LicenseDetailViewModel.Factory::class)
class LicenseDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: LicenseDetail,
    userPreferencesRepository: UserPreferencesRepository,
    licenseRepository: LicenseRepository,
) : GenericViewModel(userPreferencesRepository) {
    val license: StateFlow<License?> = licenseRepository.getLicenseByID(navKey.id)
        .map { it?.toLicense() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            initialValue = null,
        )

    @AssistedFactory
    interface Factory {
        fun create(navKey: LicenseDetail): LicenseDetailViewModel
    }
}