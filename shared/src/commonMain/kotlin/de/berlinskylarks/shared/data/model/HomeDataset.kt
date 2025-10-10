package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HomeDataset(
    @SerialName("team_id")
    val teamID: Int,
    val season: Int,
    @SerialName("league_group_id")
    val leagueGroupID: Int,
    @SerialName("league_group")
    val leagueGroup: LeagueGroup,
    @SerialName("next_game")
    val nextGame: Game?,
    @SerialName("last_game")
    val lastGame: Game?,
    @SerialName("playoff_series")
    val playoffSeries: PlayoffSeries?,
    val table: LeagueTable,
    @SerialName("table_row")
    val tableRow: LeagueTable.Row?,
    @SerialName("streak_data")
    val streakData: List<StreakDataEntry>?,
)
