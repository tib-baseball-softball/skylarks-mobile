package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fields")
data class FieldEntity(
    @PrimaryKey
    val id: Int,
    val clubId: Int?,
    val name: String,
    val addressAddon: String,
    val description: String?,
    val street: String?,
    val postalCode: String?,
    val city: String?,
    val latitude: Double?,
    val longitude: Double?,
    val spectatorTotal: Int?,
    val spectatorSeats: Int?,
    val humanCountry: String?,
    val photoUrl: String?,
)
