package de.berlinskylarks.shared.database.model

import de.berlinskylarks.shared.data.model.Person

data class PersonEntity(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
) {
    fun toPerson(): Person {
        return Person(
            id = id,
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate
        )
    }
}