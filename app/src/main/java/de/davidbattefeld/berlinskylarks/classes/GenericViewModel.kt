package de.davidbattefeld.berlinskylarks.classes

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class GenericViewModel(application: Application) : AndroidViewModel(application) {
    open var url = ""

    val api = API()

    // To be overridden by each specific view model
    open fun load() {

    }
}