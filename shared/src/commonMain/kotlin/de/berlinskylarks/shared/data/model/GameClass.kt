package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Basically the same as `League`, but used differently in API responses.
 *
 * Since BSM does not use API versioning, all we can do is map the structure when it changes.
 */
@Serializable
data class GameClass(
    var id: Int,
    var season: Int?,
    var name: String,
    var acronym: String,
    var sport: String,
    var classification: String?,
    @SerialName("age_group")
    var ageGroup: String?
)