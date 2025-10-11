package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.HomeDataset

class HomeDatasetAPIClient : DiamondPlannerAPIClient() {
    suspend fun loadHomeDatasetsForTeam(teamID: Int, season: Int): List<HomeDataset> {
        return apiCall<List<HomeDataset>>(
            resource = "api/team/favorite",
            queryParameters = mutableListOf(
                "team" to teamID.toString(),
                "season" to season.toString(),
            )
        ) ?: emptyList()
    }
}