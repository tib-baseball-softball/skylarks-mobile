package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.Index

@Entity(
    primaryKeys = ["gameReportID", "mediaID"],
    indices = [Index(value = ["gameReportID", "mediaID"])]
)
data class GameReportMediaCrossRef(
    val gameReportID: Int,
    val mediaID: Int,
)
