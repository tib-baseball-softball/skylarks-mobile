package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity(tableName = "league_groups")
data class LeagueGroupEntity(
    val id: Int,
    val name: String,
    val acronym: String,
    val season: Int,
    @Embedded(prefix = "league_")
    val league: LeagueEntity,
    @Embedded(prefix = "table_")
    val table: TableEntity,
)
