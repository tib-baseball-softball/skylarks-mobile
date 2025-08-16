package de.berlinskylarks.shared.database.model

import androidx.room.Entity

@Entity(tableName = "functionaries")
data class FunctionaryEntity (
    val id: Int,
    var category: String, //set by BSM (Enum)
    var function: String, //set by user (Freitext)
    var mail: String,
)