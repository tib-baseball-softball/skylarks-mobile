package de.berlinskylarks.shared.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import de.berlinskylarks.shared.database.dao.FunctionaryDao
import de.berlinskylarks.shared.database.dao.GameReportDao
import de.berlinskylarks.shared.database.dao.MediaDao
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.GameReportMediaCrossRef
import de.berlinskylarks.shared.database.model.MediaEntity

// The Room compiler generates the `actual` implementations.
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

@Database(
    entities = [
        FunctionaryEntity::class,
        GameReportEntity::class,
        MediaEntity::class,
        GameReportMediaCrossRef::class,
    ],
    version = 4,
    exportSchema = false,
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun functionaryDao(): FunctionaryDao
    abstract fun gameReportDao(): GameReportDao
    abstract fun mediaDao(): MediaDao
}