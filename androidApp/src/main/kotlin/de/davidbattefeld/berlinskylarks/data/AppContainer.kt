package de.davidbattefeld.berlinskylarks.data

import android.content.Context
import de.berlinskylarks.shared.data.api.FunctionaryAPIClient
import de.berlinskylarks.shared.database.getDatabase
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.berlinskylarks.shared.database.repository.OfflineFunctionaryRepository
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY

interface AppContainer {
    val functionaryRepository: FunctionaryRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val functionaryRepository: FunctionaryRepository by lazy {
        OfflineFunctionaryRepository(
            functionaryDao = getDatabase(context).functionaryDao(),
            functionaryClient = FunctionaryAPIClient(authKey = BSM_API_KEY)
        )
    }
}