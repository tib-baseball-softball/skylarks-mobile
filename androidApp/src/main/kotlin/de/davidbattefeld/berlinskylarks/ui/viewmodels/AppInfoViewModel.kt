package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.appconfigclient.models.ConfigurationDTO
import de.berlinskylarks.shared.database.repository.ConfigurationRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = AppInfoViewModel.Factory::class)
class AppInfoViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    configurationRepository: ConfigurationRepository,
    private val workManagerTiBRepository: WorkManagerTiBRepository
) : GenericViewModel(userPreferencesRepository) {

    val activeConfig: StateFlow<ConfigurationDTO?> =
        configurationRepository.getConfigurationsByApplicationContext(
            applicationContext = ApplicationContext.production
        ).map { dbConfigs ->
            dbConfigs.map {
                it.toConfigurationDTO()
            }.firstOrNull()
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun syncConfigs() {
        workManagerTiBRepository.syncConfiguration()
    }

    @AssistedFactory
    interface Factory {
        fun create(): AppInfoViewModel
    }
}