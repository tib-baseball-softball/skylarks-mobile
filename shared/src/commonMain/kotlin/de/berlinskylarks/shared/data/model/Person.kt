package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class Person(
    var id: Int,
    @JsonNames("first_name")
    var firstName: String,
    @JsonNames("last_name")
    var lastName: String,
    @JsonNames("birth_date")
    var birthDate: String,
    //there's more, but it's privacy-sensitive
)