package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayoffSeries(
    @SerialName("series_games")
    val seriesGames: List<Game>?,
    val status: PlayoffSeriesStatus,
    val teams: Map<String, PlayoffTeam>,
    @SerialName("leading_team")
    val leadingTeam: PlayoffTeam?,
    @SerialName("trailing_team")
    val trailingTeam: PlayoffTeam?,
    @SerialName("series_length")
    val seriesLength: Int,
)
