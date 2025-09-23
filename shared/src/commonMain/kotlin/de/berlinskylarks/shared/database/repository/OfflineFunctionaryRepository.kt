package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.data.api.BSMAPIClient.Companion.CLUB_ID
import de.berlinskylarks.shared.data.api.FunctionaryAPIClient
import de.berlinskylarks.shared.database.dao.FunctionaryDao
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import de.berlinskylarks.shared.database.model.PersonEntity
import kotlinx.coroutines.flow.Flow

class OfflineFunctionaryRepository(
    private val functionaryDao: FunctionaryDao,
    private val functionaryClient: FunctionaryAPIClient,
) :
    FunctionaryRepository {
    override suspend fun insertFunctionary(functionary: FunctionaryEntity) =
        functionaryDao.insert(functionary)

    override suspend fun updateFunctionary(functionary: FunctionaryEntity) =
        functionaryDao.update(functionary)

    override suspend fun deleteFunctionary(functionary: FunctionaryEntity) =
        functionaryDao.delete(functionary)

    override fun getAllFunctionariesStream(): Flow<List<FunctionaryEntity>> =
        functionaryDao.getAllFunctionaries()

    override suspend fun syncFunctionaries() {
        val functionaries = functionaryClient.loadFunctionaries(CLUB_ID)

        functionaries.forEach { functionary ->
            insertFunctionary(FunctionaryEntity(
                id = functionary.id,
                category = functionary.category,
                function = functionary.function,
                mail = functionary.mail,
                admission_date = functionary.admission_date,
                personEntity = PersonEntity(
                    id = functionary.person.id,
                    firstName = functionary.person.firstName,
                    lastName = functionary.person.lastName,
                    birthDate = functionary.person.birthDate
                )
            ))
        }
    }
}