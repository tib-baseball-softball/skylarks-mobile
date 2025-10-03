package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.Game
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey
    val id: Long,
    val matchID: String,
    val leagueID: Int?,
    val time: String,
    val dateTime: String?, // custom - ISO 8601 formatted
    val season: Int?,
    val external: Boolean,
    val json: Game,
)
