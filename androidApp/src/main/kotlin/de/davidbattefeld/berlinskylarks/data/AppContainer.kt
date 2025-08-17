package de.davidbattefeld.berlinskylarks.data

import android.content.Context
import de.berlinskylarks.shared.database.getDatabase
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.berlinskylarks.shared.database.repository.OfflineFunctionaryRepository

interface AppContainer {
    val functionaryRepository: FunctionaryRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val functionaryRepository: FunctionaryRepository by lazy {
        OfflineFunctionaryRepository(getDatabase(context).functionaryDao())
    }
}