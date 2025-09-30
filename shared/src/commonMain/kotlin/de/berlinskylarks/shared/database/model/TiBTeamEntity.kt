package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tib_teams")
data class TiBTeamEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val leagueID: Int?,
    val bsmShortName: String?
)
