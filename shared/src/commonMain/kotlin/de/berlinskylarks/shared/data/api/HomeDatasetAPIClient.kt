package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.HomeDataset
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class HomeDatasetAPIClient(
    configurationRepository: ConfigurationRepository,
) : DiamondPlannerAPIClient(configurationRepository) {
    suspend fun loadHomeDatasetsForTeam(
        teamID: Int,
        season: Int,
        gameClass: Int
    ): List<HomeDataset> {
        return apiCall<List<HomeDataset>>(
            resource = "api/team/favorite",
            queryParameters = mutableListOf(
                "team" to teamID.toString(),
                "season" to season.toString(),
                "gameClass" to gameClass.toString(),
            )
        ) ?: emptyList()
    }
}