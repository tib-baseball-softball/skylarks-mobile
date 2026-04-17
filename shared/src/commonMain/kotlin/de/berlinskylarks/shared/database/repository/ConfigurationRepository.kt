package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.shared.database.model.ConfigurationEntity
import kotlinx.coroutines.flow.Flow

interface ConfigurationRepository {
    suspend fun insertConfiguration(configuration: ConfigurationEntity)

    fun getConfigurationsByApplicationContext(applicationContext: ApplicationContext): Flow<List<ConfigurationEntity>>
}