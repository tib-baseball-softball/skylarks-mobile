package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.LeagueTable

class TablesAPIClient(authKey: String) : BSMAPIClient(authKey) {
    suspend fun loadSingleTable(id: Int): LeagueTable? {
        return apiCall(resource = "leagues/$id/table.json")
    }
}