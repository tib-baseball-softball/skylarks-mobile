package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    var id: Int,
    @SerialName("first_name")
    var firstName: String,
    @SerialName("last_name")
    var lastName: String,
    @SerialName("birth_date")
    var birthDate: String?,
    //there's more, but it's privacy-sensitive
)

/**
 * Used for the BSM `/matches` endpoint
 */
@Serializable
data class PersonReducedResponse(
    @SerialName("first_name")
    var firstName: String,
    @SerialName("last_name")
    var lastName: String,
)