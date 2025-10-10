package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.service.ClubDataSyncService

private const val TAG = "ClubDataWorker"

@HiltWorker
class ClubDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val clubDataSyncService: ClubDataSyncService,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            clubDataSyncService.syncClubData(BSMAPIClient.SKYLARKS_CLUB_ID)

            Log.d(TAG, "Club Data Worker executed")
            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "Error in club data worker", t)
            Result.retry()
        }
    }
}