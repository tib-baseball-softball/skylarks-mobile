package de.berlinskylarks.shared.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import de.berlinskylarks.shared.database.model.FunctionaryEntity

interface FunctionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(functionary: FunctionaryEntity)

    @Update
    suspend fun update(functionary: FunctionaryEntity)
}