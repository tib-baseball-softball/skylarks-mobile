package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagues")
data class LeagueEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val season: Int?,
    val classification: String?,
    val sport: String,
    val acronym: String,
    val ageGroup: String?
)
