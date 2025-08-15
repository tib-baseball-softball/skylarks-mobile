package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    var id: Int, //this appears to be the OPASO number, too!
    var first_name: String,
    var last_name: String,
    var birth_date: String,
    //there's more, but it's privacy-sensitive
)