package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.data.api.BSMAPIClient.Companion.DEFAULT_SEASON
import de.berlinskylarks.shared.data.service.ClubDataSyncService

private const val TAG = "BSMTeamDataWorker"

@HiltWorker
class BSMTeamDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val clubDataSyncService: ClubDataSyncService,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        val season = inputData.getInt(key = "season", defaultValue = DEFAULT_SEASON)

        return try {
            val count = clubDataSyncService.syncClubTeams(
                clubID = BSMAPIClient.SKYLARKS_CLUB_ID,
                season = season
            )
            Log.i(TAG, "Synced $count teams for ")
            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "Error in teams data worker", t)
            Result.retry()
        }
    }
}