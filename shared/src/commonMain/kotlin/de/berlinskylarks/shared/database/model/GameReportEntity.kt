package de.berlinskylarks.shared.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import de.berlinskylarks.shared.data.model.tib.GameReport
import de.berlinskylarks.shared.data.model.tib.GameToggle
import de.berlinskylarks.shared.data.model.tib.Media
import de.berlinskylarks.shared.data.model.tib.TibLeague

@Entity(tableName = "game_reports")
data class GameReportEntity(
    @PrimaryKey
    val uid: Int,
    val gameID: String,
    val author: String,
    @Embedded(prefix = "tibleague_")
    val league: TibLeague?,
    val gameToggle: GameToggle,
    val teaserText: String,
    val introduction: String,
    val introductionPlain: String,
    val reportFirst: String,
    val reportFirstPlain: String,
    val reportSecond: String? = null,
    val reportSecondPlain: String? = null,
    val preview: String? = null,
    val previewPlain: String? = null,
    @Embedded(prefix = "video_")
    val video: MediaEntity? = null,
    val date: String,
    val title: String,
    val team: Int,
    val slug: String,
)

data class GameReportEntityWithMedia(
    @Embedded
    val gameReportEntity: GameReportEntity,
    @Relation(
        parentColumn = "uid",
        entity = MediaEntity::class,
        entityColumn = "id",
        associateBy = Junction(
            value = GameReportMediaCrossRef::class,
            parentColumn = "gameReportID",
            entityColumn = "mediaID"
        )
    )
    val images: List<MediaEntity>,
) {
    fun toGameReport(): GameReport {
        return GameReport(
            uid = gameReportEntity.uid,
            author = gameReportEntity.author,
            league = gameReportEntity.league,
            gameToggle = gameReportEntity.gameToggle,
            teaserText = gameReportEntity.teaserText,
            introduction = gameReportEntity.introduction,
            introductionPlain = gameReportEntity.introductionPlain,
            reportFirst = gameReportEntity.reportFirst,
            reportFirstPlain = gameReportEntity.reportFirstPlain,
            reportSecond = gameReportEntity.reportSecond,
            reportSecondPlain = gameReportEntity.reportSecondPlain,
            preview = gameReportEntity.preview,
            previewPlain = gameReportEntity.previewPlain,
            gameId = gameReportEntity.gameID,
            teaserImage = listOf(),
            headerImage = listOf(),
            gallery = images.map { Media(it.id, it.title, it.alt, it.caption, it.copyright, it.url) },
            video = if (gameReportEntity.video != null) Media(
                id = gameReportEntity.video.id,
                title = gameReportEntity.video.title,
                alt = gameReportEntity.video.alt,
                caption = gameReportEntity.video.caption,
                copyright = gameReportEntity.video.copyright,
                url = gameReportEntity.video.url,
            ) else null,
            date = gameReportEntity.date,
            title = gameReportEntity.title,
            team = gameReportEntity.team,
            slug = gameReportEntity.slug,
        )
    }
}