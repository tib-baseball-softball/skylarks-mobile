package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.LicenseDao
import de.berlinskylarks.shared.database.model.LicenseEntity
import kotlinx.coroutines.flow.Flow

class OfflineLicenseRepository(
    private val licenseDao: LicenseDao
) : LicenseRepository {
    override suspend fun insertLicense(license: LicenseEntity) = licenseDao.insert(license)

    override fun getAllLicenses(): Flow<List<LicenseEntity>> = licenseDao.getAllLicenses()

    override fun getUmpireLicenses(): Flow<List<LicenseEntity>> =
        licenseDao.getLicensesByCategory("Umpire")

    override fun getScorerLicenses(): Flow<List<LicenseEntity>> =
        licenseDao.getLicensesByCategory("Scorer")

    override fun getLicenseByID(id: Int): Flow<LicenseEntity?> = licenseDao.getLicenseByID(id)
}