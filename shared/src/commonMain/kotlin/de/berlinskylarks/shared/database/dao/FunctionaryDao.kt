package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FunctionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(functionary: FunctionaryEntity)

    @Update
    suspend fun update(functionary: FunctionaryEntity)

    @Delete
    suspend fun delete(functionary: FunctionaryEntity)

    @Query("SELECT * from functionaries")
    fun getAllFunctionaries(): Flow<List<FunctionaryEntity>>

    @Query("SELECT * FROM functionaries WHERE id = :id")
    fun getFunctionaryByID(id: Int): Flow<FunctionaryEntity?>
}