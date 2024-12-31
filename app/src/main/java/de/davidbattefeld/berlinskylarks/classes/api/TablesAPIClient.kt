package de.davidbattefeld.berlinskylarks.classes.api

import de.davidbattefeld.berlinskylarks.testdata.testTable
import model.LeagueTable

class TablesAPIClient: BSMAPIClient() {
    suspend fun loadSingleTable(id: Int): LeagueTable {
        return apiCall(resource = "leagues/$id/table.json") ?: testTable
    }
}