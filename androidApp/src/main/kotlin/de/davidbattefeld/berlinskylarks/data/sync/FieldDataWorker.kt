package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import de.berlinskylarks.shared.database.repository.FieldRepository

private const val TAG = "FieldDataWorker"

@HiltWorker
class FieldDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
    private val fieldRepository: FieldRepository,
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            // TODO: add actual sync logic when available
            Log.d(TAG, "Field Data Worker executed")
            Result.success()
        } catch (t: Throwable) {
            Log.e(TAG, "Error in field data worker", t)
            Result.retry()
        }
    }
}