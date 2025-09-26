package de.berlinskylarks.shared.data.service

import de.berlinskylarks.shared.data.api.GameReportAPIClient
import de.berlinskylarks.shared.database.model.GameReportEntity
import de.berlinskylarks.shared.database.model.MediaEntity
import de.berlinskylarks.shared.database.repository.GameReportRepository
import de.berlinskylarks.shared.database.repository.MediaRepository

class GameReportService(
    private val gameReportRepository: GameReportRepository,
    private val mediaRepository: MediaRepository,
    private val gameReportClient: GameReportAPIClient,
) {
    suspend fun syncGameReports() {
        val gameReports = gameReportClient.loadGameReports()

        gameReports.forEach { gameReport ->
            gameReportRepository.insertGameReport(
                GameReportEntity(
                    uid = gameReport.uid,
                    gameID = gameReport.gameId,
                    author = gameReport.author,
                    league = gameReport.league,
                    gameToggle = gameReport.gameToggle,
                    teaserText = gameReport.teaserText,
                    introduction = gameReport.introduction,
                    introductionPlain = gameReport.introductionPlain,
                    reportFirst = gameReport.reportFirst,
                    reportFirstPlain = gameReport.reportFirstPlain,
                    reportSecond = gameReport.reportSecond,
                    reportSecondPlain = gameReport.reportSecondPlain,
                    preview = gameReport.preview,
                    previewPlain = gameReport.previewPlain,
                    video = if (gameReport.video != null) MediaEntity(
                        id = gameReport.video.id,
                        title = gameReport.video.title,
                        alt = gameReport.video.alt,
                        caption = gameReport.video.caption,
                        copyright = gameReport.video.copyright,
                        url = gameReport.video.url
                    ) else null,
                    date = gameReport.date,
                    title = gameReport.title,
                    team = gameReport.team,
                    slug = gameReport.slug,
                )
            )

            // Handle Media
            gameReport.teaserImage.forEach { media ->
                mediaRepository.insertMedia(
                    MediaEntity(
                        id = media.id,
                        title = media.title,
                        alt = media.alt,
                        caption = media.caption,
                        copyright = media.copyright,
                        url = media.url
                    )
                )
                mediaRepository.insertGameReportReference(
                    gameReportID = gameReport.uid,
                    mediaID = media.id
                )
            }

            gameReport.headerImage?.forEach { media ->
                mediaRepository.insertMedia(
                    MediaEntity(
                        id = media.id,
                        title = media.title,
                        alt = media.alt,
                        caption = media.caption,
                        copyright = media.copyright,
                        url = media.url
                    )
                )
                mediaRepository.insertGameReportReference(
                    gameReportID = gameReport.uid,
                    mediaID = media.id
                )
            }

            gameReport.gallery?.forEach { media ->
                mediaRepository.insertMedia(
                    MediaEntity(
                        id = media.id,
                        title = media.title,
                        alt = media.alt,
                        caption = media.caption,
                        copyright = media.copyright,
                        url = media.url
                    )
                )
                mediaRepository.insertGameReportReference(
                    gameReportID = gameReport.uid,
                    mediaID = media.id
                )
            }
        }
    }
}