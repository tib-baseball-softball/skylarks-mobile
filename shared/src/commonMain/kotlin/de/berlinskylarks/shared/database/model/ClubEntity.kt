package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.Club

@Entity(tableName = "clubs")
data class ClubEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val shortName: String,
    val acronym: String,
    val organizationId: Int,
    val number: Int,
    val headquarter: String,
    val mainClub: String,
    val chairman: String,
    val registeredAssociation: String,
    val court: String,
    val addressAddon: String,
    val street: String,
    val postalCode: String,
    val city: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val successes: String,
) {
    fun toClub(): Club {
        return Club(
            id = id,
            name = name,
            shortName = shortName,
            acronym = acronym,
            organizationId = organizationId,
            number = number,
            headquarter = headquarter,
            mainClub = mainClub,
            chairman = chairman,
            registeredAssociation = registeredAssociation,
            court = court,
            addressAddon = addressAddon,
            street = street,
            postalCode = postalCode,
            city = city,
            country = country,
            latitude = latitude,
            longitude = longitude,
            successes = successes,
        )
    }
}
