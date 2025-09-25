package de.berlinskylarks.shared.data.model.tib

import de.berlinskylarks.shared.data.enums.TrainingDay
import de.berlinskylarks.shared.data.enums.TrainingSeason
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Training(
    val uid: Int,
    val season: TrainingSeason,
    val day: TrainingDay,
    @JsonNames("starttime") val startTime: Int,
    @JsonNames("endtime") val endTime: Int,
    val location: String,
    val extra: String,
    val team: Int,
    @JsonNames("teamname") val teamName: String,
    @JsonNames("human_day") val humanDay: String,
    @JsonNames("human_season") val humanSeason: String
)