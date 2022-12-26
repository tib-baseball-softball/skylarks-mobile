package de.davidbattefeld.berlinskylarks.classes

import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URL

abstract class ContentLoader : ViewModel() { // check inheritance here
    open var url = ""

    suspend fun <T> fetchBSMData(type: T): T {
        val fetchResult = URL(url).readText()
        println(fetchResult)
        return Gson().fromJson(fetchResult, object: TypeToken<T>(){})
    }
}