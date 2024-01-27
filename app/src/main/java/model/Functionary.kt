package model

import kotlinx.serialization.Serializable

@Serializable
data class Functionary(
    var id: Int,
    var category: String, //set by BSM (Enum)
    var function: String, //set by user (Freitext)
    var mail: String,
    var person: Person,
)