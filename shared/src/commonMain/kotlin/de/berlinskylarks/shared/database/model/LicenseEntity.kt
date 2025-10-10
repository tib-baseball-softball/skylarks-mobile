package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.License
import de.berlinskylarks.shared.data.model.PersonReducedResponse

@Entity(tableName = "licenses")
data class LicenseEntity(
    @PrimaryKey
    var id: Int,
    var number: String,
    var validUntil: String?,
    var category: String?,
    var level: String,
    var sportAssociation: String?,
    var sleeveNumber: Int?,
    var baseball: Boolean?,
    var softball: Boolean?,
    @Embedded(prefix = "person_")
    var person: PersonEntity,
) {
    fun toLicense(): License {
        return License(
            id = id.toLong(),
            number = number,
            validUntil = validUntil,
            category = category,
            level = level,
            sportAssociation = sportAssociation,
            sleeveNumber = sleeveNumber,
            baseball = baseball,
            softball = softball,
            person = PersonReducedResponse(
                firstName = person.firstName,
                lastName = person.lastName,
            ),
        )
    }
}
