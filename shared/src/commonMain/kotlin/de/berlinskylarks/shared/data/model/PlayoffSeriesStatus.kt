package de.berlinskylarks.shared.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class PlayoffSeriesStatus(value: String) {
    @SerialName("leading")
    LEADING("leading"),

    @SerialName("tied")
    TIED("tied"),

    @SerialName("won")
    WON("won"),
}