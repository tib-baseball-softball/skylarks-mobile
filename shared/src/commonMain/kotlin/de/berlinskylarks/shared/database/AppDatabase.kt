package de.berlinskylarks.shared.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import de.berlinskylarks.shared.database.dao.BoxScoreDao
import de.berlinskylarks.shared.database.dao.ClubDao
import de.berlinskylarks.shared.database.dao.FieldDao
import de.berlinskylarks.shared.database.dao.FunctionaryDao
import de.berlinskylarks.shared.database.dao.GameDao
import de.berlinskylarks.shared.database.dao.GameReportDao
import de.berlinskylarks.shared.database.dao.LeagueGroupDao
import de.berlinskylarks.shared.database.dao.LicenseDao
import de.berlinskylarks.shared.database.dao.MediaDao
import de.berlinskylarks.shared.database.dao.PlayerDao
import de.berlinskylarks.shared.database.dao.TiBTeamDao
import de.berlinskylarks.shared.database.model.BoxScoreEntity
import de.berlinskylarks.shared.database.model.ClubEntity
import de.berlinskylarks.shared.database.model.FieldEntity
import de.berlinskylarks.shared.database.model.FunctionaryEntity
import de.berlinskylarks.shared.database.model.GameEntity
import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.GameReportMediaCrossRef
import de.berlinskylarks.shared.database.model.LeagueEntity
import de.berlinskylarks.shared.database.model.LeagueGroupEntity
import de.berlinskylarks.shared.database.model.LicenseEntity
import de.berlinskylarks.shared.database.model.MediaEntity
import de.berlinskylarks.shared.database.model.PlayerEntity
import de.berlinskylarks.shared.database.model.TableEntity
import de.berlinskylarks.shared.database.model.TiBTeamEntity

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
        LeagueGroupEntity::class,
        LeagueEntity::class,
        TableEntity::class,
        ClubEntity::class,
        FieldEntity::class,
        LicenseEntity::class,
        PlayerEntity::class,
        TiBTeamEntity::class,
    ],
    version = 9,
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
    abstract fun leagueGroupDao(): LeagueGroupDao
    abstract fun clubDao(): ClubDao
    abstract fun fieldDao(): FieldDao
    abstract fun licenseDao(): LicenseDao
    abstract fun playerDao(): PlayerDao
    abstract fun tibTeamDao(): TiBTeamDao
}