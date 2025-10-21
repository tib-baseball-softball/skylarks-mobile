package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.LicenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LicenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(license: LicenseEntity)

    @Query("SELECT * FROM licenses")
    fun getAllLicenses(): Flow<List<LicenseEntity>>

    @Query("SELECT * FROM licenses WHERE category = :category")
    fun getLicensesByCategory(category: String): Flow<List<LicenseEntity>>

    @Query("SELECT * FROM licenses WHERE id = :id")
    fun getLicenseByID(id: Int): Flow<LicenseEntity?>
}