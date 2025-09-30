package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.LeagueGroupsAPIClient
import de.berlinskylarks.shared.data.api.TablesAPIClient
import de.berlinskylarks.shared.database.model.LeagueEntity
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

            leagueGroupRepository.insertLeagueGroup(LeagueGroupEntity(
                id = leagueGroup.id,
                name = leagueGroup.name,
                acronym = leagueGroup.acronym,
                season = leagueGroup.season,
                league = LeagueEntity(
                    id = leagueGroup.league.id,
                    name = leagueGroup.league.name,
                    season = leagueGroup.league.season,
                    classification = leagueGroup.league.classification,
                    sport = leagueGroup.league.sport,
                    acronym = leagueGroup.league.acronym,
                    ageGroup = leagueGroup.league.ageGroup,
                ),
                table = TableEntity(
                    leagueID = leagueGroup.id,
                    json = table
                )
            ))
        }
        return leagueGroups.size
    }
}