package de.berlinskylarks.shared.data.model.tib

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class GameToggle(val value: String) {
    @SerialName("SG") SG("SG"),
    @SerialName("DH") DH("DH"),
}