package de.berlinskylarks.shared.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MediaEntity(
    @PrimaryKey
    val id: Int,
    val title: String? = null,
    val alt: String? = null,
    val caption: String? = null,
    val copyright: String? = null,
    val url: String
)
