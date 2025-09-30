package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Field(
    val id: Int,
    @SerialName("club_id")
    val clubId: Int?,
    val name: String,
    @SerialName("address_addon")
    val addressAddon: String,
    val description: String?,
    val street: String?,
    @SerialName("postal_code")
    val postalCode: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    @SerialName("spectator_total")
    val spectatorTotal: Int?,
    @SerialName("spectator_seats")
    val spectatorSeats: Int?,
    @SerialName("human_country")
    val humanCountry: String?,
    @SerialName("photo_url")
    val photoUrl: String?,
)