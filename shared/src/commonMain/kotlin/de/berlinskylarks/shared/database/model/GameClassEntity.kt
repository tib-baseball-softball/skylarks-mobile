package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_class")
data class GameClassEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val season: Int?,
    val classification: String?,
    val sport: String?,
    val acronym: String,
    val ageGroup: String?
)
