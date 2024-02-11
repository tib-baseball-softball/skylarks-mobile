package model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
data class SkylarksTeam(
    val uid: Int,
    val name: String,
    @JsonNames("bsm_league")
    val bsmLeague: Int,

    @JsonNames("league_id")
    val leagueID: Int,

    @JsonNames("bsm_short_name")
    val bsmShortName: String
)