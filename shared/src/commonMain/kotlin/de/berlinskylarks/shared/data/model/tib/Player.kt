package de.berlinskylarks.shared.data.model.tib

import de.berlinskylarks.shared.data.model.JSONDataObject
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class Player(
    @SerialName("uid")
    override var id: Int,
    @SerialName("bsm_id")
    val bsmID: Int,
    @SerialName("fullname")
    val fullName: String,
    @SerialName("firstname")
    val firstName: String,
    @SerialName("lastname")
    val lastName: String,
    val birthday: Int,
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
) : JSONDataObject {
    fun isCoach(): Boolean {
        return number == "C" || positions.isEmpty()
    }

    @OptIn(ExperimentalTime::class)
    fun getAge(): Int {
        val birthInstant = Instant.Companion.fromEpochSeconds(birthday.toLong())

        val birthDate = birthInstant.toLocalDateTime(TimeZone.Companion.currentSystemDefault()).date
        val currentDate = Clock.System.todayIn(TimeZone.Companion.currentSystemDefault())

        var age = currentDate.year - birthDate.year

        if (currentDate.month < birthDate.month ||
            (currentDate.month == birthDate.month && currentDate.day < birthDate.day)
        ) {
            age--
        }
        return age
    }
}