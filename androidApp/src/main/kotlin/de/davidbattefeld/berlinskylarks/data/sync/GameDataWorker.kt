package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.api.BSMAPIClient.Companion.DEFAULT_SEASON
import de.berlinskylarks.shared.data.service.GameSyncService

private const val TAG = "GameDataWorker"

@HiltWorker
class GameDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val gameSyncService: GameSyncService,
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        val season = inputData.getInt(key = "season", defaultValue = DEFAULT_SEASON)

        return try {
            val total = gameSyncService.syncGamesForSeason(season = season)
            Log.d(TAG, "Synced $total games for season $season")

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(
                TAG,
                "Error in game data worker",
                throwable
            )

            Result.retry()
        }
    }
}
