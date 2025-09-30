package de.davidbattefeld.berlinskylarks.data

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.HiltAndroidApp
import de.berlinskylarks.shared.data.api.BSMAPIClient.Companion.DEFAULT_SEASON
import de.davidbattefeld.berlinskylarks.data.sync.GameDataWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BerlinSkylarksApplication() : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    // official docs are wrong: https://stackoverflow.com/questions/78088535/getworkmanagerconfiguration-overrides-nothing
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(true)
            .build()

        makeGameSyncRequest(constraints)
        makeGameReportSyncRequest(constraints)
    }

    private fun makeGameSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)


        val gameSyncRequest = PeriodicWorkRequestBuilder<GameDataWorker>(
            repeatInterval = 4,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(workDataOf(
                "season" to DEFAULT_SEASON // only current year is automatically refreshed
            ))
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_GAME_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = gameSyncRequest,
        )
    }

    private fun makeGameReportSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)
        val gameReportSyncRequest = PeriodicWorkRequestBuilder<GameDataWorker>(
            repeatInterval = 8,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_GAME_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = gameReportSyncRequest,
        )
    }

    companion object {
        const val SYNC_GAME_DATA_WORK_NAME = "sync_game_data_work"
    }
}