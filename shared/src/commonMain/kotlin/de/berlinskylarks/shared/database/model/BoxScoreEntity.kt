package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.MatchBoxScore

@Entity(tableName = "box_scores")
data class BoxScoreEntity(
    @PrimaryKey
    val matchID: String,
    val json: MatchBoxScore,
)
