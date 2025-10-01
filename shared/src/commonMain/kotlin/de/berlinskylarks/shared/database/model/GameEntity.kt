package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.Game

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val matchID: String,
    val leagueID: Int?,
    val time: String,
    val season: Int?,
    val external: Boolean,
    val json: Game,
)
