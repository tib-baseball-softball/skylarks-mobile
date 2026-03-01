package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.database.model.GameClassEntity
import de.berlinskylarks.shared.database.model.LeagueGroupEntity
import de.berlinskylarks.shared.database.model.TableEntity
import de.berlinskylarks.shared.database.repository.LeagueGroupRepository

class LeagueGroupSyncService(
    private val leagueGroupRepository: LeagueGroupRepository,
    private val leagueGroupsAPIClient: LeagueGroupsAPIClient,
    private val tablesAPIClient: TablesAPIClient,
) {
    suspend fun syncLeagueGroups(season: Int): Int {
        val leagueGroups = leagueGroupsAPIClient.loadLeagueGroupsForClub(season)

        leagueGroups.forEach { leagueGroup ->
            val table = tablesAPIClient.loadSingleTable(leagueGroup.id)

            leagueGroupRepository.insertLeagueGroup(
                LeagueGroupEntity(
                    id = leagueGroup.id,
                    name = leagueGroup.name,
                    acronym = leagueGroup.acronym,
                    season = leagueGroup.season,
                    gameClass = GameClassEntity(
                        id = leagueGroup.gameClass.id,
                        name = leagueGroup.gameClass.name,
                        season = leagueGroup.gameClass.season,
                        classification = leagueGroup.gameClass.classification,
                        sport = leagueGroup.gameClass.sport,
                        acronym = leagueGroup.gameClass.acronym,
                        ageGroup = leagueGroup.gameClass.ageGroup,
                    ),
                    table = TableEntity(
                        leagueID = leagueGroup.id,
                        json = table
                    ),
                    classification = leagueGroup.classification,
                    humanClassification = leagueGroup.humanClassification,
                    humanClassificationShort = leagueGroup.humanClassificationShort,
                    sport = leagueGroup.sport,
                    humanSport = leagueGroup.humanSport,
                    humanSportShort = leagueGroup.humanSportShort,
                    ageGroup = leagueGroup.ageGroup,
                    plannedInnings = leagueGroup.plannedInnings,
                    humanAgeGroupShort = leagueGroup.humanAgeGroupShort,
                )
            )
        }
        return leagueGroups.size
    }
}