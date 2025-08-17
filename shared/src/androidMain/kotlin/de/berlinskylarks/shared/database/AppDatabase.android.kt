package de.berlinskylarks.shared.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver

fun getDatabase(context: Context) = Room
    .databaseBuilder(context, AppDatabase::class.java, "local.db")
    .setDriver(BundledSQLiteDriver())
    .build()