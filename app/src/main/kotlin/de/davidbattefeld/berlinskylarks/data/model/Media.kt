package de.davidbattefeld.berlinskylarks.data.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class Media(
    @JsonNames("uid")
    val id: Int,
    val title: String? = null,
    val alt: String? = null,
    val caption: String? = null,
    val copyright: String? = null,
    val url: String
)