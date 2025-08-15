package de.berlinskylarks.shared.data.enums

import kotlinx.serialization.Serializable

@Serializable
enum class TrainingDay(val value: Int) {
    Sunday(0),
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6);

    companion object {
        fun fromValue(value: Int): TrainingDay = entries.first { it.value == value }
    }
}