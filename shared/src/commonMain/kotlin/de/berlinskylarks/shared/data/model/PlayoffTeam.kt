package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PlayoffTeam(
    val name: String,
    val wins: Int,
)
