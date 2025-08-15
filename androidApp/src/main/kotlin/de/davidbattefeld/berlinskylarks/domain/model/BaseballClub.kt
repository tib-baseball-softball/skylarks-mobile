package de.davidbattefeld.berlinskylarks.domain.model

import androidx.annotation.DrawableRes

data class BaseballClub(
    var name: String,
    @field:DrawableRes
    var logo: Int,
)