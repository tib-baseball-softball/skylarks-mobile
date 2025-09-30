package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import de.berlinskylarks.shared.data.model.LeagueTable

@Entity(tableName = "tables")
data class TableEntity(
    val leagueID: Int,
    val json: LeagueTable,
)
