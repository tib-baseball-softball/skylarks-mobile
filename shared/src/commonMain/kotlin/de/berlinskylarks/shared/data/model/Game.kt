package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * The terms `Game` and `Match` are used interchangeably. Regrettable historical error.
 */
@Serializable
data class Game(
    override var id: Int,
    @SerialName("match_id")
    var matchID: String,
    @SerialName("planned_innings")
    var plannedInnings: Int?,
    var time: String,
    @SerialName("league_id")
    var leagueID: Int,
    @SerialName("home_runs")
    var homeRuns: Int?,
    @SerialName("away_runs")
    var awayRuns: Int?,
    @SerialName("home_team_name")
    var homeTeamName: String,
    @SerialName("away_team_name")
    var awayTeamName: String,
    var state: String,
    @SerialName("human_state")
    var humanState: String,
    @SerialName("scoresheet_url")
    var scoresheetURL: String?,
    var field: Field?,
    var league: League,
    @SerialName("home_league_entry")
    var homeLeagueEntry: LeagueEntry,
    @SerialName("away_league_entry")
    var awayLeagueEntry: LeagueEntry,
    @SerialName("umpire_assignments")
    var umpireAssignments: List<Assignment>,
    @SerialName("scorer_assignments")
    var scorerAssignments: List<Assignment>,
): JSONDataObject