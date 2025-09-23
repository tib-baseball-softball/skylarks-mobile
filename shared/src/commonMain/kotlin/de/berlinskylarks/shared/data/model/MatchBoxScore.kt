package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MatchBoxScore(
    val header: BoxScoreHeader,
    val linescore: Linescore,
    @SerialName("offensive_away")
    val offensiveAway: OffensiveMatchStats,
    @SerialName("additional_away")
    val additionalAway: AdditionalMatchStats,
    @SerialName("offensive_home")
    val offensiveHome: OffensiveMatchStats,
    @SerialName("additional_home")
    val additionalHome: AdditionalMatchStats,
    @SerialName("pitching_away")
    val pitchingAway: PitchingMatchStats,
    @SerialName("pitching_home")
    val pitchingHome: PitchingMatchStats,
    @SerialName("game_notes")
    val gameNotes: GameNotes,
    @SerialName("full_boxscore_html")
    val fullBoxScoreHTML: String
)

@Serializable
data class Baserunning(
    @SerialName("stolen_bases")
    val stolenBases: List<AdditionalStat>,
    @SerialName("caught_stealings")
    val caughtStealings: List<AdditionalStat>
)

@Serializable
data class Batting(
    val doubles: List<AdditionalStat>,
    val triples: List<AdditionalStat>,
    val homeruns: List<AdditionalStat>,
    @SerialName("sacrifice_hits")
    val sacrificeHits: List<AdditionalStat>,
    @SerialName("sacrifice_flys")
    val sacrificeFlys: List<AdditionalStat>
)

@Serializable
data class Fielding(
    val errors: List<AdditionalStat>,
    @SerialName("passed_balls")
    val passedBalls: List<AdditionalStat>,
    @SerialName("double_plays")
    val doublePlays: List<AdditionalStat>,
    @SerialName("triple_plays")
    val triplePlays: List<AdditionalStat>
)

@Serializable
data class GameNotes(
    val field: Field,
    @SerialName("game_duration")
    val gameDuration: String,
    val spectators: Int,
    val umpires: List<Assignment>,
    val scorers: List<Assignment>
)

@Serializable
data class BoxScoreHeader(
    val league: League,
    val season: Int,
    @SerialName("match_id")
    val matchID: String,
    val time: String
)

@Serializable
data class AdditionalMatchStats(
    @SerialName("league_entry")
    val leagueEntry: LeagueEntry,
    val batting: Batting,
    val baserunning: Baserunning,
    val fielding: Fielding
)

@Serializable
data class OffensiveMatchStats(
    @SerialName("league_entry")
    val leagueEntry: LeagueEntry,
    val lineup: List<OffensiveLineupEntry>,
    val sum: OffensiveLineupStats
)

@Serializable
data class PitchingMatchStats(
    @SerialName("league_entry")
    val leagueEntry: LeagueEntry,
    val lineup: List<PitchingLineupEntry>,
    val sum: PitchingLineupStats
)

@Serializable
data class AdditionalStat(
    val person: Person,
    val count: Int,
    val sum: Int
)
