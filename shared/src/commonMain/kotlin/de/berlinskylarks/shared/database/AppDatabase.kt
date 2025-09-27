package de.berlinskylarks.shared.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import de.berlinskylarks.shared.database.dao.BoxScoreDao
import de.berlinskylarks.shared.database.dao.FunctionaryDao
import de.berlinskylarks.shared.database.dao.GameDao
import de.berlinskylarks.shared.database.dao.GameReportDao
import de.berlinskylarks.shared.database.dao.MediaDao
import de.berlinskylarks.shared.database.model.BoxScoreEntity
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import de.berlinskylarks.shared.database.model.GameEntity
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
        GameEntity::class,
        BoxScoreEntity::class,
    ],
    version = 5,
    exportSchema = false,
)
@TypeConverters(Converters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun functionaryDao(): FunctionaryDao
    abstract fun gameReportDao(): GameReportDao
    abstract fun mediaDao(): MediaDao
    abstract fun gameDao(): GameDao
    abstract fun boxScoreDao(): BoxScoreDao
}