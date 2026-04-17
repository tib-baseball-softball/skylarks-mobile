package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.Club
import de.berlinskylarks.shared.data.model.Field
import de.berlinskylarks.shared.data.model.License
import de.berlinskylarks.shared.database.repository.ConfigurationRepository

class ClubAPIClient(
    configurationRepository: ConfigurationRepository,
    authKey: String) : BSMAPIClient(configurationRepository, authKey) {
    suspend fun getClubData(id: Int): Club? {
        return apiCall<Club>(
            resource = "clubs/$id.json"
        )
    }

    suspend fun getLicensesForClub(clubID: Int): List<License> {
        return apiCall<List<License>>(
            resource = "clubs/$clubID/licenses.json"
        ) ?: emptyList()
    }

    suspend fun getFieldsForClub(clubID: Int): List<Field> {
        return apiCall<List<Field>>(
            resource = "clubs/$clubID/fields.json"
        ) ?: emptyList()
    }
}