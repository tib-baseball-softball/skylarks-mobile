package de.davidbattefeld.berlinskylarks.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import de.berlinskylarks.shared.data.api.BSMAPIClient

private const val USER_PREFERENCES_NAME = "settings"

val DEFAULT_SETTINGS = UserPreferences(
    season = BSMAPIClient.DEFAULT_SEASON
)

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)