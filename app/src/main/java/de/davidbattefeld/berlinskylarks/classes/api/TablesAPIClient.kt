package de.davidbattefeld.berlinskylarks.classes.api

import de.davidbattefeld.berlinskylarks.testdata.testTable
import de.davidbattefeld.berlinskylarks.model.LeagueTable

class TablesAPIClient: BSMAPIClient() {
    suspend fun loadSingleTable(id: Int): LeagueTable {
        return apiCall(resource = "leagues/$id/table.json") ?: testTable
    }
}