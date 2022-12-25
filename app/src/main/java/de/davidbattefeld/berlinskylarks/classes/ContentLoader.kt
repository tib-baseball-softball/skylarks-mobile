package de.davidbattefeld.berlinskylarks.classes

import de.davidbattefeld.berlinskylarks.global.apiKey
import java.net.URL

class ContentLoader {
    var url = "https://bsm.baseball-softball.de/clubs/485/teams.json?filters[seasons][]=2022&sort[league_sort]=desc&api_key=$apiKey"

    fun fetchData() {
        val fetchResult = URL(url).readText()
        println(fetchResult)
    }
}