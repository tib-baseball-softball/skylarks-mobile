package de.berlinskylarks.shared.data.model

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Serializable
data class Player(
    @JsonNames("uid")
    override var id: Int,
    @JsonNames("fullname")
    val fullName: String,
    @JsonNames("firstname")
    val firstName: String,
    @JsonNames("lastname")
    val lastName: String,
    val birthday: Int,
    val admission: String,
    val number: String,
    val throwing: String,
    val batting: String,
    val coach: String,
    val slug: String,
    @JsonNames("teamname")
    val teamName: String?,
    val media: List<Media>,
    val positions: List<String>,
    val teams: List<SkylarksTeam>,
): JSONDataObject {
    fun isCoach(): Boolean {
        return number == "C" || positions.isEmpty()
    }

    @OptIn(ExperimentalTime::class)
    fun getAge(): Int {
        val birthInstant = Instant.fromEpochSeconds(birthday.toLong())

        val birthDate = birthInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date
        val currentDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

        var age = currentDate.year - birthDate.year

        if (currentDate.month < birthDate.month ||
            (currentDate.month == birthDate.month && currentDate.day < birthDate.day)
        ) {
            age--
        }
        return age
    }
}
