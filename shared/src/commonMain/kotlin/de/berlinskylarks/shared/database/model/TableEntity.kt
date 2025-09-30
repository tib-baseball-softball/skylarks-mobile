package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.LeagueTable

@Entity(tableName = "tables")
data class TableEntity(
    @PrimaryKey
    val leagueID: Int,
    val json: LeagueTable?,
)
