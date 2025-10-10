package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreakDataEntry(
    val game: String,
    @SerialName("won_game")
    val wonGame: Boolean,
    @SerialName("wins_count")
    val winsCount: Int,
)
