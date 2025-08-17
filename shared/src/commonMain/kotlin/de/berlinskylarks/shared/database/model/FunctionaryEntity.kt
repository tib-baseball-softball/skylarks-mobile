package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "functionaries")
data class FunctionaryEntity (
    @PrimaryKey
    val id: Int,
    var category: String, //set by BSM (Enum)
    var function: String, //set by user (Freitext)
    var mail: String,
    // var person: Person
)