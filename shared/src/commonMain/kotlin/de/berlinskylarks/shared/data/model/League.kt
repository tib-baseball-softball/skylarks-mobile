package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class League(
    var id: Int,
    var season: Int?,
    var name: String,
    var acronym: String,
    var sport: String,
    var classification: String?,
    @SerialName("age_group")
    var ageGroup: String?
)