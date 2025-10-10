package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.ClubAPIClient
import de.berlinskylarks.shared.data.api.FunctionaryAPIClient
import de.berlinskylarks.shared.database.model.ClubEntity
import de.berlinskylarks.shared.database.model.FieldEntity
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import de.berlinskylarks.shared.database.model.LicenseEntity
import de.berlinskylarks.shared.database.model.PersonEntity
import de.berlinskylarks.shared.database.repository.ClubRepository
import de.berlinskylarks.shared.database.repository.FieldRepository
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.berlinskylarks.shared.database.repository.LicenseRepository

class ClubDataSyncService(
    private val clubRepository: ClubRepository,
    private val functionaryRepository: FunctionaryRepository,
    private val licenseRepository: LicenseRepository,
    private val fieldRepository: FieldRepository,
    private val clubAPIClient: ClubAPIClient,
    private val functionaryAPIClient: FunctionaryAPIClient,
) {
    suspend fun syncClubData(clubID: Int) {
        val club = clubAPIClient.getClubData(clubID)

        club?.let { club ->
            clubRepository.insertClub(
                ClubEntity(
                    id = club.id,
                    name = club.name,
                    shortName = club.shortName,
                    acronym = club.acronym,
                    organizationId = club.organizationId,
                    number = club.number,
                    headquarter = club.headquarter,
                    mainClub = club.mainClub,
                    chairman = club.chairman,
                    registeredAssociation = club.registeredAssociation,
                    court = club.court,
                    addressAddon = club.addressAddon,
                    street = club.street,
                    postalCode = club.postalCode,
                    city = club.city,
                    country = club.country,
                    latitude = club.latitude,
                    longitude = club.longitude,
                    successes = club.successes,
                )
            )

            val functionaries = functionaryAPIClient.loadFunctionaries(clubID)
            functionaries.forEach { f ->
                functionaryRepository.insertFunctionary(
                    FunctionaryEntity(
                        id = f.id,
                        category = f.category,
                        function = f.function,
                        mail = f.mail,
                        admission_date = f.admission_date,
                        personEntity = PersonEntity(
                            id = f.person.id,
                            firstName = f.person.firstName,
                            lastName = f.person.lastName,
                            birthDate = f.person.birthDate ?: "",
                        )
                    )
                )
            }

            val fields = clubAPIClient.getFieldsForClub(clubID)
            fields.forEach { field ->
                fieldRepository.insertField(
                    FieldEntity(
                        id = field.id,
                        clubId = field.clubId,
                        name = field.name,
                        addressAddon = field.addressAddon,
                        description = field.description,
                        street = field.street,
                        postalCode = field.postalCode,
                        city = field.city,
                        latitude = field.latitude,
                        longitude = field.longitude,
                        spectatorTotal = field.spectatorTotal,
                        spectatorSeats = field.spectatorSeats,
                        humanCountry = field.humanCountry,
                        photoUrl = field.photoUrl,
                    )
                )
            }

            val licenses = clubAPIClient.getLicensesForClub(clubID)
            licenses.forEach { lic ->
                licenseRepository.insertLicense(
                    LicenseEntity(
                        id = lic.id.toInt(),
                        number = lic.number,
                        validUntil = lic.validUntil,
                        category = lic.category,
                        level = lic.level,
                        sportAssociation = lic.sportAssociation,
                        sleeveNumber = lic.sleeveNumber,
                        baseball = lic.baseball,
                        softball = lic.softball,
                        person = PersonEntity(
                            id = 0,
                            firstName = lic.person.firstName,
                            lastName = lic.person.lastName,
                            birthDate = "",
                        ),
                    )
                )
            }
        }
    }
}