package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.database.repository.TiBTeamRepository

private const val TAG = "TiBTeamDataWorker"

@HiltWorker
class TiBTeamDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val tiBTeamRepository: TiBTeamRepository,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            // TODO: add actual sync logic when available
            Log.d(TAG, "TiB Team Data Worker executed")
            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "Error in tib team data worker", t)
            Result.retry()
        }
    }
}