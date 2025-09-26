package de.davidbattefeld.berlinskylarks.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.berlinskylarks.shared.data.api.FunctionaryAPIClient
import de.berlinskylarks.shared.data.api.GameReportAPIClient
import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.api.MatchAPIClient
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.data.api.TeamsAPIClient
import de.berlinskylarks.shared.data.service.GameReportService
import de.berlinskylarks.shared.database.AppDatabase
import de.berlinskylarks.shared.database.dao.FunctionaryDao
import de.berlinskylarks.shared.database.dao.GameReportDao
import de.berlinskylarks.shared.database.dao.MediaDao
import de.berlinskylarks.shared.database.getDatabase
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.GameRepository
import de.berlinskylarks.shared.database.repository.MediaRepository
import de.berlinskylarks.shared.database.repository.OfflineFunctionaryRepository
import de.berlinskylarks.shared.database.repository.OfflineGameReportRepository
import de.berlinskylarks.shared.database.repository.OfflineMediaRepository
import de.davidbattefeld.berlinskylarks.data.preferences.dataStore
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.global.AUTH_HEADER
import de.davidbattefeld.berlinskylarks.global.BSM_API_KEY
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.dataStore

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository =
        UserPreferencesRepository(dataStore)

    @Provides
    @Singleton
    fun provideGameRepository(matchAPIClient: MatchAPIClient): GameRepository = GameRepository(matchAPIClient)

    @Provides
    @Singleton
    fun provideFunctionaryApiClient(): FunctionaryAPIClient = FunctionaryAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideTablesApiClient(): TablesAPIClient = TablesAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideLeagueGroupsApiClient(): LeagueGroupsAPIClient = LeagueGroupsAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideTeamsApiClient(): TeamsAPIClient = TeamsAPIClient(authKey = AUTH_HEADER)

    @Provides
    @Singleton
    fun provideGameReportAPIClient(): GameReportAPIClient = GameReportAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideMatchApiClient(): MatchAPIClient = MatchAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = getDatabase(context)

    @Provides
    @Singleton
    fun provideFunctionaryDao(db: AppDatabase): FunctionaryDao = db.functionaryDao()

    @Provides
    @Singleton
    fun provideGameReportDao(db: AppDatabase): GameReportDao = db.gameReportDao()

    @Provides
    @Singleton
    fun provideMediaDao(db: AppDatabase): MediaDao = db.mediaDao()

    @Provides
    @Singleton
    fun provideFunctionaryRepository(
        functionaryDao: FunctionaryDao,
        functionaryClient: FunctionaryAPIClient
    ): FunctionaryRepository = OfflineFunctionaryRepository(functionaryDao, functionaryClient)

    @Provides
    @Singleton
    fun provideMediaRepository(
        mediaDao: MediaDao
    ): MediaRepository = OfflineMediaRepository(mediaDao)

    @Provides
    @Singleton
    fun provideGameReportRepository(
        gameReportDao: GameReportDao
    ): GameReportRepository = OfflineGameReportRepository(gameReportDao)

    @Provides
    @Singleton
    fun provideGameReportService(
        gameReportRepository: OfflineGameReportRepository,
        mediaRepository: OfflineMediaRepository,
        gameReportClient: GameReportAPIClient,
    ): GameReportService = GameReportService(gameReportRepository, mediaRepository, gameReportClient)
}
