package de.berlinskylarks.shared.database.repository

import de.berlinskylarks.shared.database.dao.MediaDao
import de.berlinskylarks.shared.database.model.GameReportMediaCrossRef
import de.berlinskylarks.shared.database.model.MediaEntity

class OfflineMediaRepository(
    private val mediaDao: MediaDao
) : MediaRepository {
    override suspend fun insertMedia(media: MediaEntity) = mediaDao.insert(media)
    override suspend fun insertGameReportReference(gameReportID: Int, mediaID: Int) =
        mediaDao.insertGameReportReference(
            ref = GameReportMediaCrossRef(gameReportID, mediaID)
        )
}