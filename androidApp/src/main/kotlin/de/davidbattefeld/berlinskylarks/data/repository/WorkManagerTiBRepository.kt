package de.davidbattefeld.berlinskylarks.data.repository

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.workDataOf
import de.davidbattefeld.berlinskylarks.data.sync.GameDataWorker

class WorkManagerTiBRepository(
    context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun syncScores(season: Int) {
        val syncScoresRequest = OneTimeWorkRequestBuilder<GameDataWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setInputData(workDataOf("season" to season))
            .build()
        workManager.enqueue(syncScoresRequest)
    }
}