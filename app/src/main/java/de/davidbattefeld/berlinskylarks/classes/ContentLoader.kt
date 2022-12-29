package de.davidbattefeld.berlinskylarks.classes

import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.net.URL

abstract class ContentLoader : ViewModel() { // check inheritance here
    open var url = ""

    inline fun <reified T> parseResponse(json: String, typeToken: Type): T {
        val gson = GsonBuilder().create()
        return gson.fromJson<T>(json, typeToken)
    }

    suspend fun fetchJSONData(): String {
        return URL(url).readText()
    }
}