package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.FieldEntity
import kotlinx.coroutines.flow.Flow

interface FieldRepository {
    suspend fun insertField(field: FieldEntity)
    fun getAllFields(): Flow<List<FieldEntity>>
    fun getFieldByID(id: Int): Flow<FieldEntity?>
    fun getFieldsByClubId(clubId: Int): Flow<List<FieldEntity>>
}