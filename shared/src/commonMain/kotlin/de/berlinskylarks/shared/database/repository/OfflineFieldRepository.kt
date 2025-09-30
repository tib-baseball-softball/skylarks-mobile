package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.FieldDao
import de.berlinskylarks.shared.database.model.FieldEntity
import kotlinx.coroutines.flow.Flow

class OfflineFieldRepository(
    private val fieldDao: FieldDao
) : FieldRepository {
    override suspend fun insertField(field: FieldEntity) = fieldDao.insert(field)

    override fun getAllFields(): Flow<List<FieldEntity>> = fieldDao.getAllFields()

    override fun getFieldByID(id: Int): Flow<FieldEntity?> = fieldDao.getFieldByID(id)

    override fun getFieldsByClubId(clubId: Int): Flow<List<FieldEntity>> = fieldDao.getFieldsByClubId(clubId)
}