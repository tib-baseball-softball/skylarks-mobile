package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.FunctionaryEntity
import kotlinx.coroutines.flow.Flow

interface FunctionaryRepository {
    suspend fun insertFunctionary(functionary: FunctionaryEntity)
    suspend fun updateFunctionary(functionary: FunctionaryEntity)
    suspend fun deleteFunctionary(functionary: FunctionaryEntity)
    suspend fun getAllFunctionariesStream(): Flow<List<FunctionaryEntity>>
}