package de.davidbattefeld.berlinskylarks.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import de.davidbattefeld.berlinskylarks.data.sync.BSMTeamDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.ClubDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.GameDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.GameReportWorker
import de.davidbattefeld.berlinskylarks.data.sync.LeagueGroupWorker
import de.davidbattefeld.berlinskylarks.data.sync.PlayerDataWorker
import de.davidbattefeld.berlinskylarks.data.sync.TiBTeamDataWorker

class WorkManagerTiBRepository(
    context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun syncBSMTeams(season: Int) {
        val request = OneTimeWorkRequestBuilder<BSMTeamDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workDataOf("season" to season))
            .build()
        workManager.enqueue(request)
    }

    fun syncScores(season: Int) {
        val syncScoresRequest = OneTimeWorkRequestBuilder<GameDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workDataOf("season" to season))
            .build()
        workManager.enqueue(syncScoresRequest)
    }

    fun syncGameReports() {
        val syncGameReportsRequest = OneTimeWorkRequestBuilder<GameReportWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueue(syncGameReportsRequest)
    }

    fun syncLeagueGroups(season: Int) {
        val syncLeagueGroupsRequest = OneTimeWorkRequestBuilder<LeagueGroupWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workDataOf("season" to season))
            .build()
        workManager.enqueue(syncLeagueGroupsRequest)
    }

    fun syncClubData() {
        val request = OneTimeWorkRequestBuilder<ClubDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueue(request)
    }

    fun syncPlayers() {
        val request = OneTimeWorkRequestBuilder<PlayerDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueue(request)
    }

    fun syncTiBTeams() {
        val request = OneTimeWorkRequestBuilder<TiBTeamDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .build()
        workManager.enqueue(request)
    }
}