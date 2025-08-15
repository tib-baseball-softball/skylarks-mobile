package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class League(
    var id: Int,
    var season: Int,
    var name: String,
    var acronym: String,
    var sport: String,
    var classification: String,
    var age_group: String?
)