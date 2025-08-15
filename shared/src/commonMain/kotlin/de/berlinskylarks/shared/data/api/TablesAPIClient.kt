package de.berlinskylarks.shared.data.api

import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.berlinskylarks.shared.data.model.LeagueTable

class TablesAPIClient: BSMAPIClient() {
    suspend fun loadSingleTable(id: Int): LeagueTable {
        return apiCall(resource = "leagues/$id/table.json") ?: testTable
    }
}