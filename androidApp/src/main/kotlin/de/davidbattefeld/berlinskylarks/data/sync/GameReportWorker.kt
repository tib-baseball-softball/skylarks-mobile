package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.service.GameReportSyncService

private const val TAG = "GameDataWorker"

@HiltWorker
class GameReportWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val gameReportSyncService: GameReportSyncService,
    ): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            gameReportSyncService.syncGameReports()
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