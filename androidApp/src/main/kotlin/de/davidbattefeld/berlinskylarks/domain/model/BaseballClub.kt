package de.davidbattefeld.berlinskylarks.domain.model

import androidx.annotation.DrawableRes

class BaseballClub(
    var name: String,
    @DrawableRes
    var logo: Int,
)