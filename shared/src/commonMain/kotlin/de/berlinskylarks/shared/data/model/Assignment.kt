package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Assignment(
    var license: License
)