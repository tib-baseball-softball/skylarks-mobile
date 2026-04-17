package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.appconfigclient.models.ApplicationContext
import de.berlinskylarks.shared.database.model.ConfigurationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConfigurationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(field: ConfigurationEntity)

    @Query("SELECT * FROM configurations WHERE applicationContext = :applicationContext")
    fun findByApplicationContext(applicationContext: ApplicationContext): Flow<List<ConfigurationEntity>>
}