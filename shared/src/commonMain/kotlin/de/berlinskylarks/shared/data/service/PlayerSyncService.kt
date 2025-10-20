package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.SkylarksTeamsAPIClient
import de.berlinskylarks.shared.database.model.MediaEntity
import de.berlinskylarks.shared.database.model.PlayerEntity
import de.berlinskylarks.shared.database.model.TiBTeamEntity
import de.berlinskylarks.shared.database.repository.PlayerRepository
import de.berlinskylarks.shared.database.repository.TiBTeamRepository

class PlayerSyncService(
    private val teamClient: SkylarksTeamsAPIClient,
    private val playerRepository: PlayerRepository,
    private val tibTeamRepository: TiBTeamRepository,
) {
    suspend fun syncTeams() {
        val teams = teamClient.loadAllTeams()

        teams.forEach { team ->
            tibTeamRepository.insertTeam(
                TiBTeamEntity(
                    id = team.id,
                    name = team.name,
                    leagueID = team.leagueID,
                    bsmShortName = team.bsmShortName,
                    sport = team.sport,
                    ageGroup = team.ageGroup,
                )
            )
        }
    }

    suspend fun syncPlayers() {
        val players = teamClient.loadAllPlayers()

        players.forEach { player ->
            val firstMedia = player.media.firstOrNull()
            var mediaEntity: MediaEntity? = null
            firstMedia?.let {
                mediaEntity = MediaEntity(
                    id = it.id,
                    title = it.title,
                    alt = it.alt,
                    caption = it.caption,
                    copyright = it.copyright,
                    url = it.url
                )
            }

            playerRepository.insertPlayer(
                PlayerEntity(
                    id = player.id,
                    firstName = player.firstName,
                    lastName = player.lastName,
                    bsmID = player.bsmID,
                    fullName = player.fullName,
                    birthday = player.birthday,
                    admission = player.admission,
                    number = player.number,
                    throwing = player.throwing,
                    batting = player.batting,
                    coach = player.coach,
                    slug = player.slug,
                    teamName = player.teamName,
                    media = mediaEntity,
                    positions = player.positions.joinToString(separator = ","),
                    teams = player.teams.map { it.id }.joinToString(separator = ",")
                ),
            )
        }
    }
}