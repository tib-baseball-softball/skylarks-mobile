package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.service.PlayerSyncService
import io.sentry.kotlin.multiplatform.Sentry

private const val TAG = "PlayerDataWorker"

@HiltWorker
class PlayerDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val playerSyncService: PlayerSyncService,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            playerSyncService.syncPlayers()

            Result.success()
        } catch (t: Throwable) {
            Sentry.captureException(t)
            Result.retry()
        }
    }
}