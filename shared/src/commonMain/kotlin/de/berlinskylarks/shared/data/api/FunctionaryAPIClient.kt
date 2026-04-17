package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.Functionary
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class FunctionaryAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String) : BSMAPIClient(configurationRepository, authKey = authKey) {
    suspend fun loadFunctionaries(clubID: Int): List<Functionary> {
        return apiCall<List<Functionary>>(resource = "clubs/$clubID/club_functions.json")
            ?: emptyList()
    }
}