package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

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
)
