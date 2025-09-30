package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.LicenseEntity
import kotlinx.coroutines.flow.Flow

interface LicenseRepository {
    suspend fun insertLicense(license: LicenseEntity)
    fun getAllLicenses(): Flow<List<LicenseEntity>>
    fun getLicenseByID(id: Int): Flow<LicenseEntity?>
}