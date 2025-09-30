package de.berlinskylarks.shared.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.berlinskylarks.shared.database.model.ClubEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClubDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(club: ClubEntity)

    @Query("SELECT * FROM clubs")
    fun getAllClubs(): Flow<List<ClubEntity>>

    @Query("SELECT * FROM clubs WHERE id = :id")
    fun getClubByID(id: Int): Flow<ClubEntity?>
}