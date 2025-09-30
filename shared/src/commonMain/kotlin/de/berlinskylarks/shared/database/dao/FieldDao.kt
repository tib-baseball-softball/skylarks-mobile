package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.FieldEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FieldDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(field: FieldEntity)

    @Query("SELECT * FROM fields")
    fun getAllFields(): Flow<List<FieldEntity>>

    @Query("SELECT * FROM fields WHERE id = :id")
    fun getFieldByID(id: Int): Flow<FieldEntity?>

    @Query("SELECT * FROM fields WHERE clubId = :clubId")
    fun getFieldsByClubId(clubId: Int): Flow<List<FieldEntity>>
}