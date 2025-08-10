package de.davidbattefeld.berlinskylarks.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Serializable
@OptIn(ExperimentalSerializationApi::class)
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
    val teams: List<SkylarksTeamReduced>,
): JSONDataObject {
    fun isCoach(): Boolean {
        return number == "C" || positions.isEmpty()
    }

    fun getAge(): Int {
        val birthDate = Instant.ofEpochSecond(birthday.toLong()).atZone(ZoneId.systemDefault()).toLocalDate()
        val currentDate = LocalDate.now()
        var age = currentDate.year - birthDate.year
        if (currentDate.monthValue < birthDate.monthValue ||
            (currentDate.monthValue == birthDate.monthValue && currentDate.dayOfMonth < birthDate.dayOfMonth)) {
            age--
        }
        return age
    }
}
