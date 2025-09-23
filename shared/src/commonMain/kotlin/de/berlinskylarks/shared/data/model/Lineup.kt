package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OffensiveLineupEntry(
    val person: Person,
    val starter: Boolean,
    val order: Int,
    val positions: List<String>,
    @SerialName("human_positions_short")
    val humanPositionsShort: List<String>,
    val values: OffensiveLineupStats
)

@Serializable
data class OffensiveLineupStats(
    @SerialName("at_bats")
    val atBats: Int,
    val runs: Int,
    val hits: Int,
    @SerialName("runs_batted_in")
    val runsBattedIn: Int,
    val strikeouts: Int,
    @SerialName("base_on_balls")
    val baseOnBalls: Int,
    @SerialName("batting_average")
    val battingAverage: String? = null,
    @SerialName("on_base_plus_slugging")
    val onBasePlusSlugging: String? = null
)

@Serializable
data class PitchingLineupEntry(
    val person: Person,
    val order: Int,
    val values: PitchingLineupStats
)

@Serializable
data class PitchingLineupStats(
    @SerialName("innings_pitched")
    val inningsPitched: String,
    @SerialName("batters_faced")
    val battersFaced: Int,
    @SerialName("at_bats")
    val atBats: Int,
    val hits: Int,
    val runs: Int,
    @SerialName("earned_runs")
    val earnedRuns: Int,
    val strikeouts: Int,
    @SerialName("base_on_balls_allowed")
    val baseOnBallsAllowed: Int,
    @SerialName("earned_runs_average")
    val earnedRunsAverage: String? = null,
    @SerialName("win_loss_save")
    val winLossSave: String? = null
)