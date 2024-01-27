package model

import kotlinx.serialization.Serializable

@Serializable
data class Club(
    var id: Int,
    var name: String,
    var short_name: String,
    var acronym: String,
    var organization_id: Int,
    var number: Int,
    var headquarter: String,
    var main_club: String,
    var chairman: String,
    var registered_association: String,
    var court: String,
    var address_addon: String,
    var street: String,
    var postal_code: String,
    var city: String,
    var country: String,
    var latitude: Double,
    var longitude: Double,
    var successes: String,
)