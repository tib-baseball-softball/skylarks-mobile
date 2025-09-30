package de.berlinskylarks.shared.data.model.tib

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkylarksTeam(
    @SerialName("uid")
    val id: Int,
    val name: String,

    @SerialName("league_id")
    val leagueID: Int?,

    @SerialName("bsm_short_name")
    val bsmShortName: String?
)