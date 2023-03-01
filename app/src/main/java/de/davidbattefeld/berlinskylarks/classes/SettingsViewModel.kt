package de.davidbattefeld.berlinskylarks.classes

import android.app.Application
import android.icu.util.Calendar

class SettingsViewModel(application: Application) : GenericViewModel(application) {
    val possibleSeasons = (2021..Calendar.getInstance().get(Calendar.YEAR)).toList()
}