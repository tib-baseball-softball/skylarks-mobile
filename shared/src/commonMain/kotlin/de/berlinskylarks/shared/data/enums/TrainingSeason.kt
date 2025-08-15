package de.berlinskylarks.shared.data.enums

import kotlinx.serialization.Serializable

@Serializable
enum class TrainingSeason(val value: Int) {
    Summer(0),
    Winter(1);

    companion object {
        fun fromValue(value: Int): TrainingSeason = entries.first { it.value == value }
    }
}