package de.berlinskylarks.shared.database.model

import androidx.room.Entity

@Entity(primaryKeys = ["gameReportID", "mediaID"])
data class GameReportMediaCrossRef(
    val gameReportID: Int,
    val mediaID: Int,
)
