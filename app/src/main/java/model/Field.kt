package model

import kotlinx.serialization.Serializable

@Serializable
data class Field(
    var id: Int,
    var club_id: Int?,
    var name: String,
    var address_addon: String,
    var description: String,
    var street: String?,
    var postal_code: String?,
    var city: String?,
    var latitude: Double?,
    var longitude: Double?,
    var spectator_total: Int?,
    var spectator_seats: Int?,
    var human_country: String?,
    var photo_url: String?,
)