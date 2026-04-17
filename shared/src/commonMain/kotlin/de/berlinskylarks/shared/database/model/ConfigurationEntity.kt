package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.appconfigclient.models.ConfigurationDTO
import de.berlinskylarks.appconfigclient.models.ConfigurationDTOApiURLS
import de.berlinskylarks.appconfigclient.models.FlagWithStatusDTO
import kotlin.time.Instant

@Entity(tableName = "configurations")
data class ConfigurationEntity(
    @PrimaryKey val id: String,
    val name: String,
    val applicationContext: ApplicationContext,
    val apiURLS: ConfigurationDTOApiURLS,
    val updatedAt: Instant,
    val description: String? = null,
    val flagRelations: Map<String, FlagWithStatusDTO>? = null
) {
    fun toConfigurationDTO(): ConfigurationDTO {
        return ConfigurationDTO(
            id = id,
            name = name,
            applicationContext = applicationContext,
            apiURLS = apiURLS,
            updatedAt = updatedAt,
            description = description,
            flagRelations = flagRelations
        )
    }
}
