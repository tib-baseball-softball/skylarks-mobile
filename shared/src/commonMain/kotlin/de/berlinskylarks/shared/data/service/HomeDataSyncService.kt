package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.HomeDatasetAPIClient
import de.berlinskylarks.shared.database.model.HomeDatasetEntity
import de.berlinskylarks.shared.database.repository.HomeDatasetRepository

class HomeDataSyncService(
    private val homeDatasetRepository: HomeDatasetRepository,
    private val homeDatasetAPIClient: HomeDatasetAPIClient,
) {
    suspend fun syncHomeDatasets(teamID: Int, season: Int, gameClass: Int): Int {
        val datasets = homeDatasetAPIClient.loadHomeDatasetsForTeam(
            teamID = teamID,
            season = season,
            gameClass = gameClass,
        )
        datasets.forEach {
            homeDatasetRepository.insert(
                HomeDatasetEntity(
                    teamID = it.teamID,
                    season = it.season,
                    leagueGroupID = it.leagueGroupID,
                    json = it,
                )
            )
        }
        return datasets.size
    }
}