package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.ConfigurationAPIClient
import de.berlinskylarks.shared.database.model.ConfigurationEntity
import de.berlinskylarks.shared.database.repository.ConfigurationRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ConfigurationSyncService(
    private val configurationAPIClient: ConfigurationAPIClient,
    private val configurationRepository: ConfigurationRepository,
) {

    @OptIn(ExperimentalUuidApi::class)
    suspend fun syncConfiguration(): Int {
        val response = configurationAPIClient.getConfigs()
        if (response.success) {
            val configs = response.body()

            configs.forEach { config ->
                configurationRepository.insertConfiguration(
                    ConfigurationEntity(
                        id = config.id ?: Uuid.toString(),
                        name = config.name,
                        applicationContext = config.applicationContext,
                        apiURLS = config.apiURLS,
                        updatedAt = config.updatedAt,
                        description = config.description,
                        flagRelations = config.flagRelations,
                    )
                )
            }
            return configs.size
        }
        return 0
    }
}