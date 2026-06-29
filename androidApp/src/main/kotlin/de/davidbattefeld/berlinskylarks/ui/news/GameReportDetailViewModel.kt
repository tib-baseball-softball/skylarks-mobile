package de.davidbattefeld.berlinskylarks.ui.news

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.GameRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.ui.nav.GameReportDetail
import de.davidbattefeld.berlinskylarks.ui.viewmodels.GenericViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = GameReportDetailViewModel.Factory::class)
class GameReportDetailViewModel @AssistedInject constructor(
    userPreferencesRepository: UserPreferencesRepository,
    gameRepository: GameRepository,
    gameReportRepository: GameReportRepository,
    @Assisted private val key: GameReportDetail
) : GenericViewModel(userPreferencesRepository) {

    val gameReport: StateFlow<GameReport?> = gameReportRepository.getGameReportByGameID(key.matchID)
        .map { it?.toGameReport() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val idsSplit = key.matchID.split(",").filter { it.isNotBlank() }

    val games = idsSplit.map { id ->
        gameRepository.getGameByMatchID(id)
            .map {
                it?.let { game ->
                    GameDecorator(game.json).decorate()
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )
    }

    @AssistedFactory
    interface Factory {
        fun create(key: GameReportDetail): GameReportDetailViewModel
    }
}