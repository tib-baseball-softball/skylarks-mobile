package de.berlinskylarks.shared.data.model.tib

import de.berlinskylarks.shared.utility.DateTimeUtility
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Player(
    @SerialName("uid")
    var id: Long,
    @SerialName("bsm_id")
    val bsmID: Long,
    @SerialName("fullname")
    val fullName: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName: String,
    val birthday: Long,
    val admission: String,
    val number: String,
    val throwing: String,
    val batting: String,
    val coach: String,
    val slug: String,
    @SerialName("teamname")
    val teamName: String?,
    val media: List<Media>,
    val positions: List<String>,
    val teams: List<SkylarksTeam>,
) {
    fun isCoach(): Boolean {
        return number == "C" || positions.isEmpty()
    }

    fun getAge(): Int {
        return DateTimeUtility.getAge(birthday)
    }
}