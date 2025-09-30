package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Club(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("short_name")
    val shortName: String,
    @SerialName("acronym")
    val acronym: String,
    @SerialName("organization_id")
    val organizationId: Int,
    @SerialName("number")
    val number: Int,
    @SerialName("headquarter")
    val headquarter: String,
    @SerialName("main_club")
    val mainClub: String,
    @SerialName("chairman")
    val chairman: String,
    @SerialName("registered_association")
    val registeredAssociation: String,
    @SerialName("court")
    val court: String,
    @SerialName("address_addon")
    val addressAddon: String,
    @SerialName("street")
    val street: String,
    @SerialName("postal_code")
    val postalCode: String,
    @SerialName("city")
    val city: String,
    @SerialName("country")
    val country: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("successes")
    val successes: String,
)