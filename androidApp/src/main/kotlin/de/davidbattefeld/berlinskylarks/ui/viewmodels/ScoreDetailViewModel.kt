package de.davidbattefeld.berlinskylarks.ui.viewmodels

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.berlinskylarks.shared.data.model.MatchBoxScore
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.data.service.GameSyncService
import de.berlinskylarks.shared.database.repository.BoxScoreRepository
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.GameRepository
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.domain.service.GameDecorator
import de.davidbattefeld.berlinskylarks.ui.nav.ScoresDetail
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ScoreDetailViewModel.Factory::class)
class ScoreDetailViewModel @AssistedInject constructor(
    @Assisted val navKey: ScoresDetail,
    userPreferencesRepository: UserPreferencesRepository,
    gameRepository: GameRepository,
    gameReportRepository: GameReportRepository,
    boxScoreRepository: BoxScoreRepository,
    private val gameSyncService: GameSyncService,
) : GenericViewModel(userPreferencesRepository) {
    var game: StateFlow<GameDecorator?> =
        gameRepository.getGameByID(navKey.id)
            .map { game ->
                if (game != null) {
                    GameDecorator(game.json).decorate()
                } else {
                    null
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = null
            )

    var gameReport: StateFlow<GameReport?> =
        gameReportRepository.getGameReportByGameID(navKey.matchID)
            .map { it?.toGameReport() }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = null
            )

    var boxScore: StateFlow<MatchBoxScore?> =
        boxScoreRepository.getBoxScoreByMatchID(matchID = navKey.matchID)
            .map { it?.json }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = null
            )

    init {
        viewModelScope.launch {
            gameSyncService.syncSingleBoxScore(
                matchID = navKey.matchID,
                id = navKey.id
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: ScoresDetail): ScoreDetailViewModel
    }
}