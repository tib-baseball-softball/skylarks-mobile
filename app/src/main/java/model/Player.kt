package model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class Player(
    val uid: Int,
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
    val teamName: String,
    val media: List<Media>,
    val positions: List<String>
) {
    fun isCoach(): Boolean {
        return number == "C" || positions.isEmpty()
    }
}
