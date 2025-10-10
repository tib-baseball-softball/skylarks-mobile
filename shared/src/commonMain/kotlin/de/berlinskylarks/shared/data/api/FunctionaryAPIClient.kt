package de.berlinskylarks.shared.data.api

import de.berlinskylarks.shared.data.model.Functionary

class FunctionaryAPIClient(authKey: String) : BSMAPIClient(authKey = authKey) {
    suspend fun loadFunctionaries(clubID: Int): List<Functionary> {
        return apiCall<List<Functionary>>(resource = "clubs/$clubID/club_functions.json")
            ?: emptyList()
    }
}