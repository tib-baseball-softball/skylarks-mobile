package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.FunctionaryDao
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import kotlinx.coroutines.flow.Flow

class OfflineFunctionaryRepository(private val functionaryDao: FunctionaryDao) :
    FunctionaryRepository {
    override suspend fun insertFunctionary(functionary: FunctionaryEntity) =
        functionaryDao.insert(functionary)

    override suspend fun updateFunctionary(functionary: FunctionaryEntity) =
        functionaryDao.update(functionary)

    override suspend fun deleteFunctionary(functionary: FunctionaryEntity) =
        functionaryDao.delete(functionary)

    override suspend fun getAllFunctionariesStream(): Flow<List<FunctionaryEntity>> =
        functionaryDao.getAllFunctionaries()
}