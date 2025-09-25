package de.berlinskylarks.shared.data.model.tib

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

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