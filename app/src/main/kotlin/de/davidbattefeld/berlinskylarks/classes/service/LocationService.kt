package de.davidbattefeld.berlinskylarks.classes.service

import androidx.core.net.toUri
import de.davidbattefeld.berlinskylarks.model.Game

class LocationService {
    companion object {
        fun buildGoogleMapsURL(game: Game): String {
            val baseURL = "https://www.google.com/maps/search/"
            val builder = baseURL.toUri().buildUpon()

            builder.appendQueryParameter("api", "1")
            builder.appendQueryParameter("map_action", "map")
            builder.appendQueryParameter("query_place_id", game.field?.name)
            builder.appendQueryParameter("query", "${game.field?.latitude}, ${game.field?.longitude}")

            return builder.build().toString()
        }
    }
}