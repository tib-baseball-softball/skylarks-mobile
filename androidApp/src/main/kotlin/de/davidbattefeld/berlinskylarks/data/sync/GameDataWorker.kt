package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

private const val TAG = "GameDataWorker"

@HiltWorker
class GameDataWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(ctx, params) {
    init {
        Log.d("dev", "Game Data Worker: Initiated")
    }

    override suspend fun doWork(): Result {
        return try {
            Log.d(
                TAG,
                "Game data worker has started and will succeed. How awesome!",
            )

            Result.success()
        } catch (throwable: Throwable) {
            Log.e(
                TAG,
                "Error in game data worker",
                throwable
            )

            Result.failure()
        }
    }
}
