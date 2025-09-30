package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.data.api.BSMAPIClient.Companion.DEFAULT_SEASON
import de.berlinskylarks.shared.data.service.LeagueGroupSyncService

private const val TAG = "LeagueGroupWorker"

@HiltWorker
class LeagueGroupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val leagueGroupSyncService: LeagueGroupSyncService,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val season = inputData.getInt(key = "season", defaultValue = DEFAULT_SEASON)

        return try {
            val count = leagueGroupSyncService.syncLeagueGroups(season)
            Log.d(TAG, "Synced $count league groups")

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