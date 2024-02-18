package model

import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val uid: Int,
    val title: String? = null,
    val alt: String? = null,
    val caption: String? = null,
    val copyright: String? = null,
    val url: String
)