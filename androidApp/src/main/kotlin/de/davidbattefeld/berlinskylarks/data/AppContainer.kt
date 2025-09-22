package de.davidbattefeld.berlinskylarks.data

import android.content.Context
import de.berlinskylarks.shared.data.api.FunctionaryAPIClient
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.database.getDatabase
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.berlinskylarks.shared.database.repository.OfflineFunctionaryRepository
import de.davidbattefeld.berlinskylarks.data.preferences.dataStore
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY

interface AppContainer {
    val functionaryRepository: FunctionaryRepository
    val tablesAPIClient: TablesAPIClient
    val userPreferencesRepository: UserPreferencesRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.dataStore)
    }

    override val functionaryRepository: FunctionaryRepository by lazy {
        OfflineFunctionaryRepository(
            functionaryDao = getDatabase(context).functionaryDao(),
            functionaryClient = FunctionaryAPIClient(authKey = BSM_API_KEY)
        )
    }

    override val tablesAPIClient: TablesAPIClient by lazy {
        TablesAPIClient(authKey = BSM_API_KEY)
    }
}