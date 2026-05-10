package de.davidbattefeld.berlinskylarks.ui.news

import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import de.davidbattefeld.berlinskylarks.ui.viewmodels.GenericViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NewsViewModel.Factory::class)
class NewsViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    gameReportRepository: GameReportRepository,
    private val workManagerTiBRepository: WorkManagerTiBRepository,
) : GenericViewModel(userPreferencesRepository) {

    val gameReports: StateFlow<List<GameReport>> = gameReportRepository.getAllGameReportsStream()
        .map { entities ->
            entities.map { it.toGameReport() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun refresh() {
        viewModelScope.launch {
            workManagerTiBRepository.syncGameReports()
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): NewsViewModel
    }
}