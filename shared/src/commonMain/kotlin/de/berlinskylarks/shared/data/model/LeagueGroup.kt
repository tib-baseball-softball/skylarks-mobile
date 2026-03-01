package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeagueGroup(
    val id: Int,
    val season: Int,
    val name: String,
    val acronym: String,
    val classification: String?,
    @SerialName("human_classification")
    val humanClassification: String?,
    @SerialName("human_classification_short")
    val humanClassificationShort: String?,
    val sport: String,
    @SerialName("human_sport")
    val humanSport: String,
    @SerialName("human_sport_short")
    val humanSportShort: String,
    @SerialName("age_group")
    val ageGroup: String,
    @SerialName("human_age_group_short")
    val humanAgeGroupShort: String,
    @SerialName("planned_innings")
    val plannedInnings: Long,
    @SerialName("game_class")
    val gameClass: GameClass,
)