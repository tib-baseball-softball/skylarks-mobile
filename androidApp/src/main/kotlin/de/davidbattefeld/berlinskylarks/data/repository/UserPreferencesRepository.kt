package de.davidbattefeld.berlinskylarks.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import de.berlinskylarks.shared.data.api.BSMAPIClient
import de.berlinskylarks.shared.utility.BSMUtility
import de.davidbattefeld.berlinskylarks.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class UserPreferencesRepository(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys {
        val SEASON_KEY = intPreferencesKey("season")
        val FAVORITE_TEAM_KEY = intPreferencesKey("favoriteTeamID")
    }

    companion object {
        private const val TAG: String = "UserPreferencesRepo"
    }

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateSelectedSeason(season: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SEASON_KEY] = season
        }
    }

    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val season = preferences[PreferencesKeys.SEASON_KEY] ?: BSMAPIClient.DEFAULT_SEASON
        val favoriteTeamID =
            preferences[PreferencesKeys.FAVORITE_TEAM_KEY] ?: BSMUtility.NON_EXISTENT_ID

        return UserPreferences(season, favoriteTeamID)
    }
}