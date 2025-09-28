package de.davidbattefeld.berlinskylarks.data.sync

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

private const val TAG = "GameDataWorker"

class GameDataWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
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
