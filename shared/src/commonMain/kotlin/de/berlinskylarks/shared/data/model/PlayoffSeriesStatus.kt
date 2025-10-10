package de.berlinskylarks.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class PlayoffSeriesStatus(value: String) {
    LEADING("leading"),
    TIED("tied"),
    WON("won"),
}