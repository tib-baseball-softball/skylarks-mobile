package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    var id: Int,
    val bsmID: Int,
    val fullName: String,
    val firstName: String,
    val lastName: String,
    val birthday: Int,
    val admission: String,
    val number: String,
    val throwing: String,
    val batting: String,
    val coach: String,
    val slug: String,
    val teamName: String?,
    @Embedded(prefix = "media_")
    val media: MediaEntity?,
    val positions: String, // concatenated
    val teams: String, // concatenated
)
