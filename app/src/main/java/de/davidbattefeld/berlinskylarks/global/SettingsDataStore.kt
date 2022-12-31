package de.davidbattefeld.berlinskylarks.global

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

suspend fun Context.writeString(key: String, value: String) {
    dataStore.edit { pref -> pref[stringPreferencesKey(key)] = value }
}

fun Context.readString(key: String): Flow<String> {
    return dataStore.data.map{ pref ->
        pref[stringPreferencesKey(key)] ?: ""
    }
}

suspend fun Context.writeInt(key: String, value: Int) {
    dataStore.edit { pref -> pref[intPreferencesKey(key)] = value }
}

fun Context.readInt(key: String): Flow<Int> {
    return dataStore.data.map { pref ->
        pref[intPreferencesKey(key)] ?: 0
    }
}

suspend fun Context.writeDouble(key: String, value: Double) {
    dataStore.edit { pref -> pref[doublePreferencesKey(key)] = value }
}

fun Context.readDouble(key: String): Flow<Double> {
    return dataStore.data.map { pref ->
        pref[doublePreferencesKey(key)] ?: 0.0
    }
}

suspend fun Context.writeLong(key: String, value: Long) {
    dataStore.edit { pref -> pref[longPreferencesKey(key)] = value }
}

fun Context.readLong(key: String): Flow<Long> {
    return dataStore.data.map { pref ->
        pref[longPreferencesKey(key)] ?: 0L
    }
}

suspend fun Context.writeBool(key: String, value: Boolean) {
    dataStore.edit { pref -> pref[booleanPreferencesKey(key)] = value }
}

fun Context.readBool(key: String): Flow<Boolean> {
    return dataStore.data.map { pref ->
        pref[booleanPreferencesKey(key)] ?: false
    }
}