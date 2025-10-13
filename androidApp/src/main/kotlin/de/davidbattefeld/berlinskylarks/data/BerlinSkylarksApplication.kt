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
import de.davidbattefeld.berlinskylarks.data.sync.BSMTeamDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.GameDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.GameReportWorker
import de.davidbattefeld.berlinskylarks.data.sync.HomeDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.LeagueGroupWorker
import de.davidbattefeld.berlinskylarks.data.sync.TiBTeamDataWorker
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
        makeHomeDataSyncRequest(constraints)
        makeGameReportSyncRequest(constraints)
        makeLeagueGroupSyncRequest(constraints)
        makeTibTeamsSyncRequest(constraints)
        makeBSMTeamsSyncRequest(constraints)
        makePlayersSyncRequest(constraints)
    }

    private fun makeBSMTeamsSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)

        val teamsSyncRequest = PeriodicWorkRequestBuilder<BSMTeamDataWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_TEAMS_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = teamsSyncRequest,
        )
    }

    private fun makeHomeDataSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)

        val homeDataSyncRequest = PeriodicWorkRequestBuilder<HomeDataWorker>(
            repeatInterval = 4,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_HOME_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = homeDataSyncRequest
        )
    }

    private fun makeGameSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)


        val gameSyncRequest = PeriodicWorkRequestBuilder<GameDataWorker>(
            repeatInterval = 4,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(
                workDataOf(
                    "season" to DEFAULT_SEASON // only current year is automatically refreshed
                )
            )
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_GAME_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = gameSyncRequest,
        )
    }

    private fun makeGameReportSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)
        val gameReportSyncRequest = PeriodicWorkRequestBuilder<GameReportWorker>(
            repeatInterval = 8,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_GAME_REPORT_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = gameReportSyncRequest,
        )
    }

    private fun makeLeagueGroupSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)
        val leagueGroupSyncRequest = PeriodicWorkRequestBuilder<LeagueGroupWorker>(
            repeatInterval = 4,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setInputData(
                workDataOf(
                    "season" to DEFAULT_SEASON
                )
            )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_LEAGUE_GROUP_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = leagueGroupSyncRequest,
        )
    }

    private fun makeTibTeamsSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)
        val leagueGroupSyncRequest = PeriodicWorkRequestBuilder<TiBTeamDataWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_TIB_TEAMS_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = leagueGroupSyncRequest,
        )
    }

    private fun makePlayersSyncRequest(constraints: Constraints) {
        val workManager = WorkManager.getInstance(applicationContext)
        val leagueGroupSyncRequest = PeriodicWorkRequestBuilder<TiBTeamDataWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_PLAYER_DATA_WORK_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = leagueGroupSyncRequest,
        )
    }

    companion object {
        const val SYNC_GAME_DATA_WORK_NAME = "sync_game_data_work"
        const val SYNC_GAME_REPORT_DATA_WORK_NAME = "sync_game_report_data_work"
        const val SYNC_LEAGUE_GROUP_DATA_WORK_NAME = "sync_league_group_data_work"
        const val SYNC_TIB_TEAMS_DATA_WORK_NAME = "sync_tib_teams_data_work"
        const val SYNC_PLAYER_DATA_WORK_NAME = "sync_player_data_work"
        const val SYNC_TEAMS_DATA_WORK_NAME = "sync_teams_data_work"
        const val SYNC_HOME_DATA_WORK_NAME = "sync_home_data_work"
    }
}