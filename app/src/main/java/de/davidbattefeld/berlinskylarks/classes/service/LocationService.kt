package de.davidbattefeld.berlinskylarks.classes.service

import android.net.Uri
import model.Game

class LocationService {
    companion object {
        fun buildGoogleMapsURL(game: Game): String {
            val baseURL = "https://www.google.com/maps/search/"
            val builder = Uri.parse(baseURL).buildUpon()

            builder.appendQueryParameter("api", "1")
            builder.appendQueryParameter("map_action", "map")
            builder.appendQueryParameter("query_place_id", game.field?.name)
            builder.appendQueryParameter("query", "${game.field?.latitude}, ${game.field?.longitude}")

            return builder.build().toString()
        }
    }
}