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
import de.berlinskylarks.shared.data.service.GameReportSyncService
import de.berlinskylarks.shared.data.service.GameSyncService
import de.berlinskylarks.shared.data.service.LeagueGroupSyncService
import de.berlinskylarks.shared.data.service.PlayerSyncService
import de.berlinskylarks.shared.database.AppDatabase
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
import de.berlinskylarks.shared.database.getDatabase
import de.berlinskylarks.shared.database.repository.BoxScoreRepository
import de.berlinskylarks.shared.database.repository.ClubRepository
import de.berlinskylarks.shared.database.repository.FieldRepository
import de.berlinskylarks.shared.database.repository.FunctionaryRepository
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.GameRepository
import de.berlinskylarks.shared.database.repository.LeagueGroupRepository
import de.berlinskylarks.shared.database.repository.LicenseRepository
import de.berlinskylarks.shared.database.repository.MediaRepository
import de.berlinskylarks.shared.database.repository.OfflineBoxScoreRepository
import de.berlinskylarks.shared.database.repository.OfflineClubRepository
import de.berlinskylarks.shared.database.repository.OfflineFieldRepository
import de.berlinskylarks.shared.database.repository.OfflineFunctionaryRepository
import de.berlinskylarks.shared.database.repository.OfflineGameReportRepository
import de.berlinskylarks.shared.database.repository.OfflineGameRepository
import de.berlinskylarks.shared.database.repository.OfflineLeagueGroupRepository
import de.berlinskylarks.shared.database.repository.OfflineLicenseRepository
import de.berlinskylarks.shared.database.repository.OfflineMediaRepository
import de.berlinskylarks.shared.database.repository.OfflinePlayerRepository
import de.berlinskylarks.shared.database.repository.OfflineTiBTeamRepository
import de.berlinskylarks.shared.database.repository.PlayerRepository
import de.berlinskylarks.shared.database.repository.TiBTeamRepository
import de.davidbattefeld.berlinskylarks.data.preferences.dataStore
import de.davidbattefeld.berlinskylarks.data.repository.UserPreferencesRepository
import de.davidbattefeld.berlinskylarks.data.repository.WorkManagerTiBRepository
import de.davidbattefeld.berlinskylarks.domain.service.CalendarService
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
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideFunctionaryApiClient(): FunctionaryAPIClient =
        FunctionaryAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideTablesApiClient(): TablesAPIClient = TablesAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideLeagueGroupsApiClient(): LeagueGroupsAPIClient =
        LeagueGroupsAPIClient(authKey = BSM_API_KEY)

    @Provides
    @Singleton
    fun provideTeamsApiClient(): TeamsAPIClient = TeamsAPIClient(authKey = AUTH_HEADER)

    @Provides
    @Singleton
    fun provideGameReportAPIClient(): GameReportAPIClient =
        GameReportAPIClient(authKey = BSM_API_KEY)

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
    fun provideGameDao(db: AppDatabase): GameDao = db.gameDao()

    @Provides
    @Singleton
    fun provideBoxScoreDao(db: AppDatabase): BoxScoreDao = db.boxScoreDao()

    @Provides
    @Singleton
    fun provideLeagueGroupDao(db: AppDatabase): LeagueGroupDao = db.leagueGroupDao()

    @Provides
    @Singleton
    fun provideClubDao(db: AppDatabase): ClubDao = db.clubDao()

    @Provides
    @Singleton
    fun provideFieldDao(db: AppDatabase): FieldDao = db.fieldDao()

    @Provides
    @Singleton
    fun provideLicenseDao(db: AppDatabase): LicenseDao = db.licenseDao()

    @Provides
    @Singleton
    fun providePlayerDao(db: AppDatabase): PlayerDao = db.playerDao()

    @Provides
    @Singleton
    fun provideTiBTeamDao(db: AppDatabase): TiBTeamDao = db.tibTeamDao()

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository =
        UserPreferencesRepository(dataStore)

    @Provides
    @Singleton
    fun provideGameRepository(matchAPIClient: MatchAPIClient, gameDao: GameDao): GameRepository =
        OfflineGameRepository(matchAPIClient, gameDao)

    @Provides
    @Singleton
    fun provideBoxScoreRepository(boxScoreDao: BoxScoreDao): BoxScoreRepository =
        OfflineBoxScoreRepository(boxScoreDao)

    @Provides
    @Singleton
    fun provideLeagueGroupRepository(leagueGroupDao: LeagueGroupDao): LeagueGroupRepository =
        OfflineLeagueGroupRepository(leagueGroupDao)

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
    fun provideClubRepository(
        clubDao: ClubDao
    ): ClubRepository = OfflineClubRepository(clubDao)

    @Provides
    @Singleton
    fun provideFieldRepository(
        fieldDao: FieldDao
    ): FieldRepository = OfflineFieldRepository(fieldDao)

    @Provides
    @Singleton
    fun provideLicenseRepository(
        licenseDao: LicenseDao
    ): LicenseRepository = OfflineLicenseRepository(licenseDao)

    @Provides
    @Singleton
    fun providePlayerRepository(
        playerDao: PlayerDao
    ): PlayerRepository = OfflinePlayerRepository(playerDao)

    @Provides
    @Singleton
    fun provideTiBTeamRepository(
        tiBTeamDao: TiBTeamDao
    ): TiBTeamRepository = OfflineTiBTeamRepository(tiBTeamDao)

    @Provides
    @Singleton
    fun provideWorkManagerTiBRepository(
        @ApplicationContext context: Context
    ): WorkManagerTiBRepository = WorkManagerTiBRepository(context)

    @Provides
    @Singleton
    fun provideGameSyncService(
        gameRepository: GameRepository,
        gameClient: MatchAPIClient,
        boxScoreRepository: BoxScoreRepository,
    ): GameSyncService = GameSyncService(gameRepository, boxScoreRepository, gameClient)

    @Provides
    @Singleton
    fun provideGameReportSyncService(
        gameReportRepository: GameReportRepository,
        mediaRepository: MediaRepository,
        gameReportClient: GameReportAPIClient,
    ): GameReportSyncService =
        GameReportSyncService(gameReportRepository, mediaRepository, gameReportClient)

    @Provides
    @Singleton
    fun provideLeagueGroupSyncService(
        leagueGroupRepository: LeagueGroupRepository,
        leagueGroupsAPIClient: LeagueGroupsAPIClient,
        tablesAPIClient: TablesAPIClient,
    ): LeagueGroupSyncService =
        LeagueGroupSyncService(leagueGroupRepository, leagueGroupsAPIClient, tablesAPIClient)

    @Provides
    @Singleton
    fun providePlayerSyncService(
        teamClient: TeamsAPIClient,
        playerRepository: PlayerRepository,
        tibTeamRepository: TiBTeamRepository,
    ): PlayerSyncService = PlayerSyncService(teamClient, playerRepository, tibTeamRepository)

    @Provides
    @Singleton
    fun provideCalendarService(@ApplicationContext context: Context): CalendarService =
        CalendarService(context)
}
