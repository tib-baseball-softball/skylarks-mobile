package de.davidbattefeld.berlinskylarks.data.model

import de.davidbattefeld.berlinskylarks.data.enums.TrainingDay
import de.davidbattefeld.berlinskylarks.data.enums.TrainingSeason
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
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

