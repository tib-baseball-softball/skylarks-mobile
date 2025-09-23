package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import de.berlinskylarks.shared.data.model.Functionary

@Entity(tableName = "functionaries")
data class FunctionaryEntity (
    @PrimaryKey
    val id: Int,
    var category: String, //set by BSM (Enum)
    var function: String, //set by user (Freitext)
    var mail: String,
    var admission_date: String,
    @Embedded(prefix = "person") var personEntity: PersonEntity
) {
    fun toFunctionary(): Functionary {
        return Functionary(
            id = id,
            category = category,
            function = function,
            mail = mail,
            person = personEntity.toPerson(),
            admission_date = admission_date
        )
    }
}