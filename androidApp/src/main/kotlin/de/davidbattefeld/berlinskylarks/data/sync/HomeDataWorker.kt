package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.service.HomeDataSyncService
import de.berlinskylarks.shared.utility.BSMUtility

private const val TAG = "HomeDataWorker"

@HiltWorker
class HomeDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val homeDataSyncService: HomeDataSyncService,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val teamID = inputData.getInt("teamID", defaultValue = BSMUtility.NON_EXISTENT_ID)
        val season = inputData.getInt("season", defaultValue = BSMAPIClient.DEFAULT_SEASON)

        if (teamID == BSMUtility.NON_EXISTENT_ID) {
            Log.e(TAG, "Team ID is invalid")
            return Result.failure()
        }

        return try {
            homeDataSyncService.syncHomeDatasets(teamID, season)
            Log.i(TAG, "Successfully synced home datasets")
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, "Failed to sync home datasets", throwable)
            Result.retry()
        }
    }
}