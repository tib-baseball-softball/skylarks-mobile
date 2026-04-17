package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.service.ConfigurationSyncService

private const val TAG = "ConfigurationWorker"

@HiltWorker
class ConfigurationWorker @AssistedInject constructor(
    @Assisted cts: Context,
    @Assisted params: WorkerParameters,
    private val configurationSyncService: ConfigurationSyncService,
): CoroutineWorker(cts, params) {
    override suspend fun doWork(): Result {
        return try {
            val count = configurationSyncService.syncConfiguration()
            Log.i(TAG, "Synced configuration with $count items")
            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "Error in teams data worker", t)
            Result.retry()
        }
    }
}