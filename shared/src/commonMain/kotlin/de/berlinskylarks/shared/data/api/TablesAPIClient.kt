package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.LeagueTable
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class TablesAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String
) : BSMAPIClient(configurationRepository, authKey) {
    suspend fun loadSingleTable(id: Int): LeagueTable? {
        return apiCall(resource = "leagues/$id/table.json")
    }
}