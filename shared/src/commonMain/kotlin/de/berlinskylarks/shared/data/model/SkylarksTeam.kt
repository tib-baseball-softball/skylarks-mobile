package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class SkylarksTeam(
    @JsonNames("uid")
    val id: Int,
    val name: String,

    @JsonNames("league_id")
    val leagueID: Int?,

    @JsonNames("bsm_short_name")
    val bsmShortName: String?
)