package de.davidbattefeld.berlinskylarks.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class SkylarksTeam(
    @JsonNames("uid")
    val id: Int,
    val name: String,
    @JsonNames("bsm_league")
    val bsmLeague: Int,

    @JsonNames("league_id")
    val leagueID: Int,

    @JsonNames("bsm_short_name")
    val bsmShortName: String
)

/**
 * TODO: remove when server response is correct again
 */
@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class SkylarksTeamReduced(
    @JsonNames("uid")
    val id: Int,
    val name: String,
)