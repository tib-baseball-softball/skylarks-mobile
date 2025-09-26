package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.model.MediaEntity

interface MediaRepository {
    suspend fun insertMedia(media: MediaEntity)
    suspend fun insertGameReportReference(gameReportID: Int, mediaID: Int)

}