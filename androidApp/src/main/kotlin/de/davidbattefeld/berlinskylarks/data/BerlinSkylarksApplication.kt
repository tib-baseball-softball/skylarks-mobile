package de.davidbattefeld.berlinskylarks.data

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import de.davidbattefeld.berlinskylarks.data.sync.GameDataWorker
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class BerlinSkylarksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val workManager = WorkManager.getInstance(applicationContext)
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        val gameSyncRequest = PeriodicWorkRequestBuilder<GameDataWorker>(4, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_GAME_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = gameSyncRequest,
        )
    }

    companion object {
        const val SYNC_GAME_DATA_WORK_NAME = "sync_game_data_work"
    }
}