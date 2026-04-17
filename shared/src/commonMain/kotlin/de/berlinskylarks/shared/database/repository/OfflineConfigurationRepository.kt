package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.shared.database.dao.ConfigurationDao
import de.berlinskylarks.shared.database.model.ConfigurationEntity
import kotlinx.coroutines.flow.Flow

class OfflineConfigurationRepository(private val configurationDao: ConfigurationDao) :
    ConfigurationRepository {
    override suspend fun insertConfiguration(configuration: ConfigurationEntity) =
        configurationDao.insert(configuration)

    override fun getConfigurationsByApplicationContext(applicationContext: ApplicationContext): Flow<List<ConfigurationEntity>> =
        configurationDao.findByApplicationContext(applicationContext)
}